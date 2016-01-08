import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class countInversions {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String s=System.getProperty("user.dir");
		//System.out.println(s);
		ArrayList<Integer> array=readFromFile();
		System.out.println(SortAndCount(array));

	}
	
	public static long SortAndCount(ArrayList<Integer> inputArray){
		long inverstions=0;
		
		if(inputArray.size()>=2){
			int halfindex=inputArray.size()/2;
			ArrayList<Integer> left= new ArrayList<Integer>(inputArray.subList(0, halfindex));
			ArrayList<Integer> right=new ArrayList<Integer>(inputArray.subList(halfindex, inputArray.size()));
			long x=SortAndCount(left);
			long y=SortAndCount(right);
			long z=MergeAndCountInvertions(left,right,inputArray);
			inverstions=x+y+z;
		}
		return inverstions;
		
		
	}
	
	public static long MergeAndCountInvertions(ArrayList<Integer> left,ArrayList<Integer> right, ArrayList<Integer> inputArray){
		long inversions=0;
		ArrayList<Integer> orderedArray=new ArrayList<Integer>();
		int n=left.size()+right.size();
		int i,j,k;
		i=0;j=0;
		
		for(k=0;k<n;k++){
			if(i<left.size()&&j<right.size()){
			if(left.get(i)<=right.get(j)){
				orderedArray.add(left.get(i));
				i++;
			}
			else{
				orderedArray.add(right.get(j));
				j++;
				inversions+=(left.size()-i);
			}
			}
			else{
			if(i==left.size()){
				while(j<right.size()){
					orderedArray.add(right.get(j));
					j++;
				}
			}
			else if(j==right.size()){
				while(i<left.size()){
					orderedArray.add(left.get(i));
					i++;
				}
			}
			}
		}
		inputArray.clear();
		inputArray.addAll(orderedArray);
		return inversions;
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
