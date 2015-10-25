#!/bin/sh
for i in 1 2 3 4 5
	do
		echo test $i
		java Project < test$i > test$i.mine
		diff test$i.mine test$i.expected
  done
