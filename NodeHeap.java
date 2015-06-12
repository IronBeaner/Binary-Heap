/**
 * CS 241: Data Structures
 * Professor: Edwin Rodriguez
 *
 * Programming Assignment #1
 *
 * <Implementing Max Heap In Node Form>
 *
 * <Oscar Nevarez>
 *  
 */
package edu.csupomona.cs.cs241.prog_assgmnt_1;
import java.util.ArrayList;
import java.util.Stack;

public class NodeHeap<V extends Comparable <V>> implements Heap<V>  {
	Node root=null;
	int numberOfNodes=0;
	

	@Override
	public void add(V value) {
		Node temp;
		if (root==null){
			root=new Node(value);
			root.parent=null;
			System.out.println("added Node: "+root.value);
		}
		else{	
			temp=traverseTree(true);
	
			if(temp.leftChild==null){
				temp.leftChild=new Node(value);
				temp.leftChild.parent=temp;
				temp=temp.leftChild;
				System.out.println("added Node: "+temp.value);
			}
			else{
				temp.rightChild=new Node(value);
				temp.rightChild.parent=temp;
				temp=temp.rightChild;
				System.out.println("added Node: "+temp.value);
			}
			siftUp(temp);
			System.out.println("The root value is: "+root.value);
			try{
			System.out.println("Its L Child is: "+root.leftChild.value);
			System.out.println("Its R Child is: "+root.rightChild.value);
			}
			catch(Exception e){
				//blah blah blah XD
			}	
		}
		numberOfNodes++;
	}
	@Override
	public V[] toArray(V[] array) {
		
		V[] nodeHeapToArray=(V[])java.lang.reflect.Array.newInstance
					(array.getClass().getComponentType(),numberOfNodes);
		
		if(numberOfNodes==0)
			return null;
		Node movingNode=root;
		
		ArrayList<Node> queue= new ArrayList<Node>();
		queue.add(movingNode);
		for(int x=0;x<numberOfNodes;x++){
			movingNode=queue.remove(0);
				if(movingNode.leftChild!=null)
					queue.add(movingNode.leftChild);
				if(movingNode.rightChild!=null)
					queue.add(movingNode.rightChild);
				
				nodeHeapToArray[x]=movingNode.value;
		}
		
		return nodeHeapToArray;
	}
	@Override
	public V remove() {
		Node temp=root;
		Node nodeToSwap=traverseTree(false);
		Node nodeToSwapsParent=nodeToSwap.parent;
		if(numberOfNodes==0)
			return null;
		if(numberOfNodes==1){
			root=null;
			numberOfNodes--;
			return temp.value;
		}
		if(numberOfNodes==2){
			V temp2=root.value;
			root.value=temp.leftChild.value;
			root.leftChild.parent=null;
			root.leftChild=null;
			numberOfNodes--;
			return temp2;
		}
		else if(nodeToSwapsParent.rightChild!=null){
		nodeToSwap.leftChild=root.leftChild;
		nodeToSwap.rightChild=root.rightChild;
		nodeToSwapsParent.rightChild=null;
		nodeToSwap.parent=null;
		root=nodeToSwap;
		System.out.println(root.value+" will be the new root value");
		System.out.println(root.value+" is the new root value, Sifting Down!");
		System.out.println(root.leftChild.value+" is its  L child ");
		System.out.println(root.rightChild.value+" is its R child");
		}
		else {
			nodeToSwap.leftChild=root.leftChild;
			nodeToSwap.rightChild=root.rightChild;
			nodeToSwapsParent.leftChild=null;
			nodeToSwap.parent=null;
			root=nodeToSwap;
			System.out.println(root.value+ " will be the new root value");
			System.out.println(root.value+" is the new root value, Sifting Down!");
			if(root.leftChild!=null)
			System.out.println(root.leftChild.value+" is its  L child ");
			if(root.rightChild!=null)
			System.out.println(root.rightChild.value+" is its R child");
		}
		numberOfNodes--;
		siftDown(root);
		return temp.value;
	}

	@Override
	public void fromArray(V[] array) {
		for(int x=0;x<array.length;x++){
			add(array[x]);
		}
	}

	@Override
	public V[] getSortedContents(V[] array) {
		V[] nodeHeapToArray=(V[])java.lang.reflect.Array.newInstance
				(array.getClass().getComponentType(),numberOfNodes);
		nodeHeapToArray=toArray(nodeHeapToArray);
		return heapSort(nodeHeapToArray);
	}
	private V[] heapSort(V[] array){
		
		//array is already in max heap form!
		//no need to make the array into heap.
		
		if(array.length==1)
			return array;
	
		int unsorted=array.length;
		System.out.println(" ");
		System.out.println("Sorted contents below:");
		while(unsorted>1){
			unsorted--;
			/*System.out.println(" ");
			System.out.println("Before Swap");
			for(int x=0;x<array.length;x++){
				System.out.print(array[x]+" ");
			}
			System.out.println(" ");*/
			swap(array,0,unsorted);
			/*System.out.println("After Swap");
			for(int x=0;x<array.length;x++){
				System.out.print(array[x]+" ");
			}
			System.out.println(" ");*/
			siftDownArray(array,unsorted-1);
			/*System.out.println("After Siftdown");
			for(int x=0;x<array.length;x++){
				System.out.print(array[x]+" ");
				
			}
			System.out.println(" ");
			*/
		}
		return array;
		
	}
	/** behavior
	*   post : swaps the first array element with the last element.
	*          
	*
	*
	* 
	@param The array that stores the values that will be swapped
	@param The value that will be swapped with the last
	@param The value that will be swapped with the first.
	**/
	private void swap(V[] array,int swapFirstElement, int swapLastElement ){
		V temp=array[swapFirstElement];
		array[swapFirstElement]=array[swapLastElement];
		array[swapLastElement]=temp;
	}
	/** behavior
	*   post : swaps the first array element with the last element.
	*          
	*
	*
	* 
	@param The array that stores the values that will be sifted-down
	@param The range the method will sift down a value
	**/
	private void siftDownArray(V[] array,int unsorted){
		int positionInArray=0;
		int swap=positionInArray;
		int bigChild=0;
		
		while (positionInArray * 2 + 1 <= unsorted){ //if not left child then no right child
			bigChild=positionInArray * 2 + 1;	
			if(array[swap].compareTo(array[bigChild])==-1){
				swap=bigChild;
			}
			if(positionInArray *2 + 2 <=unsorted){
				if(array[positionInArray *2 + 2].compareTo(array[swap])==1)
				swap=positionInArray *2 + 2;
			}
			if(positionInArray!=swap){
			swap(array,positionInArray,swap);
			positionInArray=swap;	
			}
			else 
				return;
		}
	}
	/** behavior
	 * 	pre  : Heap is not in max heap form. ie not a heap
	*   post : Heap satisfies Max Heap property 
	*          
	*
	*
	* 
	@param determines the path of traversal, if true will traverse to where the new node should go
	* else will traverse to where the last node was added
	*
	**/
	private void siftUp(Node addedNode){
		
		if(addedNode.parent==null)
			return;
		
		if(addedNode.value.compareTo(addedNode.parent.value)==1){
			System.out.println("added Node: "+addedNode.value);
			System.out.println("its parent is: "+addedNode.parent.value);
			System.out.println(addedNode.parent.value+ " Must Move Down");
						
			siftUp(swapNode(addedNode,addedNode.parent,true));
		}
	}
	/** behavior
	 * 	pre  : Heap is not in max heap form. ie not a heap
	*   post : Heap satisfies Max Heap property 
	*          
	*
	*
	* 
	@param determines the path of traversal, if true will traverse to where the new node should go
	* else will traverse to where the last node was added
	*
	**/
	private void siftDown(Node nodeToPushDown){
		
		//only one node in heap
		if(nodeToPushDown.leftChild==null&&nodeToPushDown.rightChild==null)
			return;
		if(nodeToPushDown.leftChild!=null&&nodeToPushDown.rightChild!=null){
		
			//This is used to check if the current node and its two children are equal, if so nothing is done to the heap since it satisfies the property!
			 if(nodeToPushDown.value.compareTo(nodeToPushDown.leftChild.value)==0&&nodeToPushDown.value.compareTo(nodeToPushDown.rightChild.value)==0)
				return;
		
		
			else if(nodeToPushDown.value.compareTo(nodeToPushDown.leftChild.value)==-1||
					nodeToPushDown.value.compareTo(nodeToPushDown.rightChild.value)==-1){
			
					if(nodeToPushDown.leftChild.value.compareTo(nodeToPushDown.rightChild.value)==1){
						siftDown(swapNode(nodeToPushDown,nodeToPushDown.leftChild,false));
					}
					else{
						siftDown(swapNode(nodeToPushDown,nodeToPushDown.rightChild,false));
					}
				}	
		}
		//if right child is missing but the left child is greater than parent.
		//make the parent the left child and the left child the parent
		else if((nodeToPushDown.rightChild==null&&nodeToPushDown.leftChild!=null)&&nodeToPushDown.leftChild.value.compareTo(nodeToPushDown.value)==1){
			siftDown(swapNode(nodeToPushDown,nodeToPushDown.leftChild,false));
			return;
		}
		System.out.println(root.value+" is the new root");
	}
	/** behavior
	*   post : 
	*          
	*
	*
	* 
	@param The node that might swap places or not.
	@param The node that will be compared to
	@param The boolean that determines which function to execute.
	@return The position or node that was swapped to.
	**/
	private Node swapNode(Node nodeToMove,Node comparingNode,boolean siftUp){
		if(siftUp){
			System.out.println("Greater Node IS "+nodeToMove.value);
			System.out.println("Lesser Node IS "+comparingNode.value);
		V temp=comparingNode.value;
		comparingNode.value=nodeToMove.value;
		nodeToMove.value=temp;
		return comparingNode;
		}
		else{
			V temp=nodeToMove.value;
			nodeToMove.value=comparingNode.value;
			comparingNode.value=temp;
			nodeToMove=comparingNode;
			return nodeToMove;
		}
	}
	/** behavior
	*   post : 
	*          
	*
	*
	* 
	@param determines the path of traversal, if true will traverse to where the new node should go
	* else will traverse to where the last node was added
	@return The node that must be operated on.
	**/
	private Node traverseTree(boolean addNode){
		String movements;
		Node temp=root;
		if(addNode){
			movements=Integer.toBinaryString((numberOfNodes+1));
			movements=movements.substring(1);
		}
		else{
			movements=Integer.toBinaryString(numberOfNodes);
			movements=movements.substring(1);
		}
		for(int x=0;x<movements.length();x++){
			if(movements.charAt(x)=='0'&&temp.leftChild!=null)
				temp=temp.leftChild;			
			if(movements.charAt(x)=='1'&&temp.rightChild!=null)
				temp=temp.rightChild;
		}
		return temp;
	}
	private class Node {
		protected V value;
		protected Node parent;
		protected Node leftChild;
		protected Node rightChild;
		
		public Node(V value){
			this.value=value;
		}

			
	}
}

