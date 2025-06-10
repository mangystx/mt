namespace MTLab4ChangingOrder;

public class Table
{
    private readonly SemaphoreSlim[] _forks = new SemaphoreSlim[5];

    public Table()
    {
        for (var i = 0; i < _forks.Length; i++)
        {
            _forks[i] = new SemaphoreSlim(1);
        }
    }

    public void TakeFork(int forkIndex)
    {
        _forks[forkIndex].Wait();
    }

    public void ReleaseFork(int forkIndex)
    {
        _forks[forkIndex].Release();
    }
}