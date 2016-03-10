					"# Multiple_Table_Demo" 
						多表查询demo
=========================左外连接======================
	select * from Monday left join Tuesday on Monday.name=Tuesday.name
	
	左外连接包含left join左表所有行，如果左表中某行在右表没有匹配，
	则结果中对应行右表的部分全部为空(NULL).
=========================左外连接======================



不支持-----------------------------
=========================右外连接======================
	select * from Monday right join Tuesday on Monday.name=Tuesday.name
			right join 或 right outer join
	
	右外连接包含right join右表所有行，如果左表中某行在右表没有匹配，
	则结果中对应左表的部分全部为空(NULL)
=========================右外连接======================


不支持-----------------------------
=========================完全外连接======================
	select * from Monday full join Tuesday on Monday.name=Tuesday.name
			full join 或 full outer join
	
	完全外连接包含full join左右两表中所有的行，如果右表中某行在左表中没有匹配，
	则结果中对应行右表的部分全部为空(NULL)，如果左表中某行在右表中没有匹配，
	则结果中对应行左表的部分全部为空(NULL)。
=========================右外连接======================


=========================内连接======================
	select * from Monday join Tuesday on Monday.name=Tuesday.name
			join 或 inner join
	等价于
	select * from Monday,Tuesday where Monday.name=Tuesday.name
	
	查询匹配条件的项.....
=========================内连接======================



=========================交叉链接======================
	select * from Monday cross join Tuesday
			cross join
	
	没有 WHERE 子句的交叉联接将产生连接所涉及的表的笛卡尔积。
	第一个表的行数乘以第二个表的行数等于笛卡尔积结果集的大小。
	返回 =第一个表的行数乘以第二个表的行数的集合
=========================交叉链接======================






[image](https://github.com/majunm/ThunderN/raw/master/t0.png)
[image](https://github.com/majunm/ThunderN/raw/master/t1.png)