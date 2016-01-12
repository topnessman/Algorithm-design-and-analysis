import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class sortandcount {

	public static void main(String[] args){
		ArrayList<Integer> array=readFromFile();
		//sortandcountMethod cannot have the same name as its class
		//sortandcountMethod returns int type variable, representing times of comparing
		//sortandcountMethod changes the array parameter passed into that. (important programming idea in java)
		int timesofcount=sortandcountMethod(array);
		//output the sorted version of array
		for(Integer i: array){
			System.out.print(i+",");
		}
		System.out.println();
		//print out times of comparing
		System.out.println(timesofcount);
	
	}
	
	public static int sortandcountMethod(ArrayList<Integer> inputArray){
		
		int count=0;
		//sortandcountMethod is a recursive method. Needs left and right part to recursively
		//call this method. However, left and right are not constructed during the function call.
		//We need to construct them in advance, and change them by passing them to the sortandcountMethod
		ArrayList<Integer> left=new ArrayList<Integer>();
		ArrayList<Integer> right=new ArrayList<Integer>();
		//THe implementation of changing inputArray. Rather than performing changing operations
		//on inputArray directly, we simply construct a new ArrayList<Integer> to store the 
		//sorted verstion of the inputArray. Then, simply clear the data in inputArray, and
		//copy the sorted array to inputArray, which is equivelant to directly changing inputArray. 
		ArrayList<Integer> orderedArray=new ArrayList<Integer>();
		//If encountering base situation , simply skip this trunk of code and return 0 and do
		//nothing on inputArray.(Already sorted)
		if(inputArray.size()>=2){
			//increment count at each iteration by m-1
			count+=(inputArray.size()-1);
			//begin to partition the inputArray.
			partition_pivot_as_first_element(inputArray,left,right);
			//partition_pivot_as_last_element(inputArray,left,right);
			//partition_intelligently(inputArray,left,right);
			
			//recursively call sortandcountMethod on left and right. Increment count by their
			//number of comparisions because total= (m-1)+# of left+#of right.
			//after calling sortandcountMethod, left and right are both changed to sorted version!
			//parameter passed in can be changed after the function call. This is amazing!
			count+=sortandcountMethod(left);
			count+=sortandcountMethod(right);
			//rearrange the inputArray to final sorted verstion.
			orderedArray.addAll(left);
			orderedArray.add(inputArray.get(0));
			orderedArray.addAll(right);
			//copy 
			inputArray.clear();
			inputArray.addAll(orderedArray);
		}
		
		return count;
		
	}
	//implements the third partition rule
	public static void partition_intelligently(ArrayList<Integer> inputArray,ArrayList<Integer> left,
			ArrayList<Integer> right){
		int first=inputArray.get(0);
		int last=inputArray.get(inputArray.size()-1);
		int median=inputArray.get(getMedianIndex(inputArray.size()));
		
		ArrayList<Integer> compareArray=new ArrayList<Integer>();
		compareArray.add(first);
		compareArray.add(median);
		compareArray.add(last);
		//Collections.sort() method sorts the compareArray
		Collections.sort(compareArray);
		if(compareArray.get(1)==first){
			 partition_pivot_as_first_element(inputArray,left,right);
		}
		else if(compareArray.get(1)==last){
			partition_pivot_as_last_element(inputArray,left,right);	 
		}
		else{
			partition_pivot_as_median_element(inputArray,left,right);
		}
	}
	//extract into a separate method 
	public static int getMedianIndex(int size){
		if(size%2==0){
			return size/2-1;
		}
		else{
			return size/2;
		}
	}
	//implements the first partition rule
	//in order to get the left and right ArrayList<Integer>, have passed them as parameter to
	//the method. 
	public static void partition_pivot_as_first_element(ArrayList<Integer> inputArray,ArrayList<Integer> left,
			ArrayList<Integer> right){
		int i,j;
		//Doesn't need to consider i-0,j=0 situation
		i=1;
		//get the pivot
		int pivot=inputArray.get(0);
		//iterating over the array to check
		for(j=1;j<inputArray.size();j++){
			if(inputArray.get(j)<pivot){
				//swap A[j] with A[i] if A[j]<pivot
				int tmp=inputArray.get(i);
				inputArray.set(i,inputArray.get(j));
				inputArray.set(j, tmp);
				//boundary index should increment by one because a new smaller element has come in
				i++;
				//j++;//No need. j increments in outer for loop
			}
		}
		//change left and right to its rightful ArrayList<Integer>
		left.addAll(inputArray.subList(1,i));
		right.addAll(inputArray.subList(i,inputArray.size()));
		
	}
	
	public static void partition_pivot_as_last_element(ArrayList<Integer> inputArray,ArrayList<Integer> left,
			ArrayList<Integer> right){
		//swpa first element with the last element before partition subroutine. Rather than redesign a completely new
		//method.
		int lastIndex=inputArray.size()-1;
		int tmp=inputArray.get(0);
		inputArray.set(0, inputArray.get(lastIndex));
		inputArray.set(lastIndex, tmp);
		//calls the original partition method which takes the first element as pivot
		partition_pivot_as_first_element(inputArray,left,right);
	}
	
	public static void partition_pivot_as_median_element(ArrayList<Integer> inputArray,ArrayList<Integer> left,
			ArrayList<Integer> right){
		//swap the median pivot with the first element before partition subroutine
		int pivotIndex=getMedianIndex(inputArray.size());
		int pivot=inputArray.get(pivotIndex);
		inputArray.set(pivotIndex, inputArray.get(0));
		inputArray.set(0, pivot);
		//calls the original partition method which takes the first element as pivot
		partition_pivot_as_first_element(inputArray,left,right);
	}
	
	public static ArrayList<Integer> readFromFile(){
		ArrayList<Integer> returnArray=new ArrayList<Integer>();
		FileReader fr=null;
		BufferedReader br=null;
		try{
		fr=new FileReader("1.txt");
		br=new BufferedReader(fr);
		String readout=null;
		while((readout=br.readLine())!=null){
			returnArray.add(new Integer(readout));
		}
		}catch(FileNotFoundException e){
			System.out.println("FileNotFound");
		}catch(IOException e){
			System.out.println("IOException");
		}finally{
			try{
				br.close();
			}catch(IOException e){
				System.out.println("IOException");
			}
			
		}
		return returnArray;
	}
}
