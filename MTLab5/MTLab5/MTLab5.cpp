﻿#include <iostream>
#include <vector>
#include <climits>
#include <omp.h>
#include <random>
#include <chrono>

using namespace std;
using Clock = chrono::high_resolution_clock;

const int ROW_COUNT = 500;
const int COL_COUNT = 500;
const int REPETITIONS = 100;

void generateRandomMatrix(vector<vector<int>>& matrix, int numThreads) {
    unsigned seed = static_cast<unsigned>(Clock::now().time_since_epoch().count());

#pragma omp parallel num_threads(numThreads)
    {
        mt19937 generator(seed + omp_get_thread_num());
        uniform_int_distribution<> dist(0, 99);

#pragma omp for
        for (int i = 0; i < ROW_COUNT; ++i)
            for (int j = 0; j < COL_COUNT; ++j)
                matrix[i][j] = dist(generator);
    }
}

int calculateMatrixSum(const vector<vector<int>>& matrix, int numThreads) {
    int totalSum = 0;

#pragma omp parallel for num_threads(numThreads) reduction(+:totalSum)
    for (int i = 0; i < ROW_COUNT; ++i)
        for (int j = 0; j < COL_COUNT; ++j)
            totalSum += matrix[i][j];

    return totalSum;
}

void findRowWithMinSum(const vector<vector<int>>& matrix, int& minSum, int& minRowIndex, int numThreads) {
    int globalMin = INT_MAX;
    int globalIndex = -1;

#pragma omp parallel num_threads(numThreads)
    {
        int localMin = INT_MAX;
        int localIndex = -1;

#pragma omp for
        for (int i = 0; i < ROW_COUNT; ++i) {
            int rowSum = 0;
            for (int j = 0; j < COL_COUNT; ++j)
                rowSum += matrix[i][j];

            if (rowSum < localMin) {
                localMin = rowSum;
                localIndex = i;
            }
        }

#pragma omp critical
        {
            if (localMin < globalMin) {
                globalMin = localMin;
                globalIndex = localIndex;
            }
        }
    }

    minSum = globalMin;
    minRowIndex = globalIndex;
}

int main() {
    const int numThreads = 12;
    vector<vector<int>> matrix(ROW_COUNT, vector<int>(COL_COUNT));

    auto initStart = Clock::now();
    generateRandomMatrix(matrix, numThreads);
    auto initEnd = Clock::now();
    double initTime = chrono::duration<double>(initEnd - initStart).count();

    cout << "Matrix initialized in: " << initTime << " seconds\n";

    omp_set_nested(1);

    int finalSum = 0;
    int minRowSum = 0;
    int minRowIndex = 0;

    double avgSumTime = 0.0;
    double avgMinTime = 0.0;

    auto benchStart = Clock::now();

#pragma omp parallel sections num_threads(2)
    {
#pragma omp section
        {
            double totalTime = 0;
            for (int i = 0; i < REPETITIONS; ++i) {
                auto t0 = Clock::now();
                finalSum = calculateMatrixSum(matrix, numThreads);
                auto t1 = Clock::now();
                totalTime += chrono::duration<double>(t1 - t0).count();
            }
            avgSumTime = totalTime / REPETITIONS;
        }

#pragma omp section
        {
            double totalTime = 0;
            for (int i = 0; i < REPETITIONS; ++i) {
                auto t0 = Clock::now();
                findRowWithMinSum(matrix, minRowSum, minRowIndex, numThreads);
                auto t1 = Clock::now();
                totalTime += chrono::duration<double>(t1 - t0).count();
            }
            avgMinTime = totalTime / REPETITIONS;
        }
    }

    auto benchEnd = Clock::now();
    double benchDuration = chrono::duration<double>(benchEnd - benchStart).count();

    cout << "\nThreads used             : " << numThreads << "\n";
    cout << "Total benchmark duration : " << benchDuration << " seconds\n";
    cout << "Average matrix sum time  : " << avgSumTime << " seconds, result = " << finalSum << "\n";
    cout << "Average min row sum time : " << avgMinTime << " seconds, minSum = " << minRowSum << ", rowIndex = " << minRowIndex << "\n";
    cout << "------------------------------------------------------------\n";

    return 0;
}