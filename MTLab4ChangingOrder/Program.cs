using MTLab4ChangingOrder;

var table = new Table();

for (var i = 0; i < 5; i++)
{
	new Philosopher(i, table);
}