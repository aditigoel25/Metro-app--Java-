import java.util.ArrayList;
import java.util.HashMap;

public class Heap<T extends Comparable<T>>{
    ArrayList<T> data= new ArrayList<>();//T is generic class and can be replaced with any type
    HashMap<T,Integer> map= new HashMap<>();
    public void add(T item){
        data.add(item);
        map.put(item, data.size()-1);
        unheapify(data.size()-1);//use to rearrange the heap when new item is added
    }
    public void unheapify(int ci)
    {//used to rearrange heap when an item is added
        int pi= (ci-1)/2; // to find parent of ci
        if(isLarger(data.get(ci),data.get(pi))>0){
            swap(pi,ci);
            unheapify(pi);
        }
    }
    private void swap(int i,int j){
        //private classes are used for isolation and data hiding
        T ith= data.get(i);
        T jth= data.get(j);
        data.set(j, ith);
        data.set(i, jth);
        map.put(jth, i);
        map.put(ith, j);

    }
    public void display(){
        System.out.println(data);
    }
    public int size(){
        return this.data.size();}
    public boolean isEmpty(){
        return this.size()==0;
    }
    public T remove(){
        swap(0, data.size()-1);
        T rv= data.remove(this.data.size()-1);
        downheapify(0);
        map.remove(rv);
        return rv;
    }
    private void downheapify(int pi){
        //downheapify is to rearrange used when an item is removed
        int lci= pi*2+1;
        int rci= pi*2+2;
        int mini= pi;
        if(lci<this.data.size() && isLarger(data.get(lci),data.get(pi))>0){
            mini=lci;
        }
        if(rci<this.data.size() && isLarger(data.get(rci),data.get(pi))>0){
            mini=rci;
        }
        if(mini != pi){
            swap(mini, pi);
            downheapify(mini);
        }
    }
    public T get()
    {
        return this.data.get(0);
    }
    public int isLarger(T t, T o){
        return t.compareTo(o);
    }
    public void updatePriority(T pair){
        int index= map.get(pair);
        unheapify(index);
    }


}

