select title from products;

select * from products 
where price = 9.99 and category = 8
order by category, price;

select * from products
where category = 8 or category = 15;

select * from products
where price between 10 and 20;

select * from orders
where orderdate between '2004-01-05' and '2004-02-05';

select customerid, count(customerid)
from orders
group by customerid;

select customerid, orderdate , sum (totalamount) as totalsum
from orders
where totalamount > 100
group by customerid, orderdate;

select c.firstname, c.lastname, p.title , ol.orderdate
from customers c
join orders o on c.customerid = o.customerid 
join orderlines ol on o.orderid = ol.orderid 
join products p on ol.prod_id = p.prod_id;