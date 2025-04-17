namespace MTLab1;

public class Stopper(List<MyThread> threads)
{
    private readonly Random _random = new();

    public void StartWork()
    {
        new Thread(() =>
        {
            foreach (var thread in threads)
            {
                Thread.Sleep(_random.Next(300, 1500));
                thread.NeedToStop = true;
            }
        }).Start();
    }
}