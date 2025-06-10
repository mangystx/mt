namespace MTLab4Waiter;

public class Waiter
{
	private readonly SemaphoreSlim _permission = new(4, 4);

    public void RequestPermission()
    {
        _permission.Wait();
    }

    public void ReleasePermission()
    {
        _permission.Release();
    }
}