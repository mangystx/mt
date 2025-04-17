namespace MTLab1;

public class MyThread
{
    public bool NeedToStop { get; set; }

    public void Start() => new Thread(Calculator).Start();
    
    private void Calculator()
    {
        long sum = 0;
        do
        {
            sum++;
        } while (!NeedToStop);
        
        Console.WriteLine(sum);
    }
}