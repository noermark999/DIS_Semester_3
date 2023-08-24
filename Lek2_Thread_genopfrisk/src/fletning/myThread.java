package fletning;

import java.util.ArrayList;
import java.util.List;

public class myThread extends Thread {
    private List<Integer> list = new ArrayList<>();

    public myThread(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        FletteSortering sortering = new FletteSortering();
        sortering.mergesort(list,0,list.size()-1);
    }

    public List<Integer> getList() {
        return list;
    }
}
