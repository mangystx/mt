namespace MTLab4Waiter;

public class Philosopher
{
	private Table _table;
    private readonly Waiter _waiter;
    private readonly int _philosopherId;
    private readonly int _leftFork;
    private readonly int _rightFork;

    public Philosopher(int id, Table table, Waiter waiter)
    {
        _philosopherId = id;
        _table = table;
        _waiter = waiter;
        _rightFork = id;
        _leftFork = (id + 1) % 5;
        var thread = new Thread(Run);
        thread.Start();
    }

    private void Run()
    {
        for (var i = 0; i < 10; i++)
        {
            Console.WriteLine($"Philosopher {_philosopherId} is thinking ({i + 1})");

            _waiter.RequestPermission();

            _table.TakeFork(_rightFork);
            _table.TakeFork(_leftFork);

            Console.WriteLine($"Philosopher {_philosopherId} is eating ({i + 1})");

            _table.ReleaseFork(_rightFork);
            _table.ReleaseFork(_leftFork);

            _waiter.ReleasePermission();
        }
    }
}