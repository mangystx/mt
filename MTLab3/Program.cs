const int bufferCapacity = 5;
const int totalItems = 20;
const int producerCount = 3;
const int consumerCount = 2;

var access = new Semaphore(1, 1);         
var full = new Semaphore(bufferCapacity, bufferCapacity);
var empty = new Semaphore(0, bufferCapacity);

var buffer = new List<string>();
var allThreads = new List<Thread>();

var producerWorkload = Distribute(totalItems, producerCount);
var consumerWorkload = Distribute(totalItems, consumerCount);

for (var i = 0; i < producerCount; i++)
{
	var id = i + 1;
	var items = producerWorkload[i];
	var thread = new Thread(() => Producer(id, items));
	thread.Start();
	allThreads.Add(thread);
}

for (var i = 0; i < consumerCount; i++)
{
	var id = i + 1;
	var items = consumerWorkload[i];
	var thread = new Thread(() => Consumer(id, items));
	thread.Start();
	allThreads.Add(thread);
}

Console.WriteLine("Очікуємо завершення потоків... Натисніть будь-яку клавішу після завершення.");
Console.ReadKey();

return;

void Producer(int id, int items)
{
	for (var i = 0; i < items; i++)
	{
		full.WaitOne();
		access.WaitOne();

		buffer.Add($"item P{id}-{i}");
		Console.WriteLine($"[Виробник {id}] додав item P{id}-{i}. Буфер: {buffer.Count}");

		access.Release();
		empty.Release();

		Thread.Sleep(100);
	}
	Console.WriteLine($"[Виробник {id}] завершив роботу.");
}

void Consumer(int id, int items)
{
	for (var i = 0; i < items; i++)
	{
		empty.WaitOne();
		Thread.Sleep(150);
		access.WaitOne();

		var item = buffer.First();
		buffer.RemoveAt(0);
		Console.WriteLine($"[Споживач {id}] забрав {item}. Буфер: {buffer.Count}");

		access.Release();
		full.Release();
	}
	Console.WriteLine($"[Споживач {id}] завершив роботу.");
}

List<int> Distribute(int total, int count)
{
	var result = new List<int>();
	var baseAmount = total / count;
	var extra = total % count;

	for (var i = 0; i < count; i++)
		result.Add(baseAmount + (i < extra ? 1 : 0));

	return result;
}