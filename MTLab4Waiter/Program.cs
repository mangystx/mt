using MTLab4Waiter;

Table table = new();
Waiter waiter = new();

for (var i = 0; i < 5; i++)
{
    new Philosopher(i, table, waiter);
}