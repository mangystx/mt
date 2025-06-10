namespace MTLab4ChangingOrder;

public class Philosopher
{
    private readonly Table _table;
    private readonly int _leftForkIndex, _rightForkIndex;
    private readonly int _philosopherId;

    public Philosopher(int philosopherId, Table table)
    {
        _philosopherId = philosopherId;
        _table = table;
        
        if (philosopherId == 0)
        {
            _leftForkIndex = philosopherId;
            _rightForkIndex = (philosopherId + 1) % 5;
        }
        else
        {
            _rightForkIndex = philosopherId;
            _leftForkIndex = (philosopherId + 1) % 5;
        }

        new Thread(Run).Start();
    }

    private void Run()
    {
        for (var iteration = 0; iteration < 10; iteration++)
        {
            Console.WriteLine($"Philosopher {_philosopherId} is thinking ({iteration + 1} time)");

            _table.TakeFork(_rightForkIndex);
            _table.TakeFork(_leftForkIndex);

            Console.WriteLine($"Philosopher {_philosopherId} is eating ({iteration + 1} time)");

            _table.ReleaseFork(_leftForkIndex);
            _table.ReleaseFork(_rightForkIndex);
        }
    }
}