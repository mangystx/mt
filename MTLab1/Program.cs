using MTLab1;

var threadList = CreateThreadList(5);

foreach (var thread in threadList)
{
    thread.Start();
}

new Thread(new Stopper(threadList).StartWork).Start();
return;

List<MyThread> CreateThreadList(int n) => Enumerable.Range(0, n).Select(_ => new MyThread()).ToList();