using MTLab2;

const int size = 100_000_000_0;
const int maxValue = 15000;
const int threadCount = 5;
const int rangeSize = size / threadCount;

var arr = GetArray(size, maxValue);

var results = new MinResult[threadCount];
var threads = new Thread[threadCount];

for (var t = 0; t < threadCount; t++)
{
	var threadIndex = t;
	var start = threadIndex * rangeSize;
	var end = threadIndex == threadCount - 1 ? size : start + rangeSize;

	threads[t] = new Thread(() =>
	{
		var localMin = int.MaxValue;
		var localMinIndex = -1;

		for (var i = start; i < end; i++)
		{
			if (arr[i] < localMin)
			{
				localMin = arr[i];
				localMinIndex = i;
			}
		}

		results[threadIndex] = new MinResult { Value = localMin, Index = localMinIndex };
	});

	threads[t].Start();
}

foreach (var thread in threads)
	thread.Join();

var globalMin = int.MaxValue;
var globalIndex = -1;

for (var i = 0; i < threadCount; i++)
{
	if (results[i].Value < globalMin)
	{
		globalMin = results[i].Value;
		globalIndex = results[i].Index;
	}
}

Console.WriteLine($"Min value: {globalMin}, index: {globalIndex}");

return;

static int[] GetArray(int size, int maxValue)
{
	var array = new int[size];
	var rand = new Random();
	
	for (var i = 0; i < size; i++)
	{
		array[i] = rand.Next(0, maxValue + 1);
	}
	
	var randomIndex = rand.Next(0, size);
	array[randomIndex] = -1;
	
	return array;
}