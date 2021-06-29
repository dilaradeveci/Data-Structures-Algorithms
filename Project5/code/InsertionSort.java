package code;

import given.AbstractArraySort;

public class InsertionSort<K extends Comparable<K>> extends AbstractArraySort<K> {

	public InsertionSort()
	{
		name = "Insertionsort";
	}

	@Override
	public void sort(K[] inputArray) 
	{   
		//TODO: Implement the insertion sort algorithm
		int j = 0;
		for (int i = 1; i < inputArray.length; i++) { 
			j = i; 
			while (j > 0 && (compare(inputArray[j-1], inputArray[j]) > 0)) { 
				swap(inputArray, j, j-1);
				j--; 
			} 
		} 
	}
}
