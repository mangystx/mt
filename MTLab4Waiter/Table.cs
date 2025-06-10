namespace MTLab4Waiter;

public class Table
{
	private readonly SemaphoreSlim[] _forks = new SemaphoreSlim[5];

    public Table()
    {
        for (var i = 0; i < 5; i++)
        {
            _forks[i] = new SemaphoreSlim(1, 1);
        }
    }

    public void TakeFork(int id)
    {
        _forks[id].Wait();
    }

    public void ReleaseFork(int id)
    {
        _forks[id].Release();
    }
}