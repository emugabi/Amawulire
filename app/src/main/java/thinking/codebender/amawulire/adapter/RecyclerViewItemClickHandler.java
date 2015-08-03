package thinking.codebender.amawulire.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by codebender on 7/27/15.
 */
public class RecyclerViewItemClickHandler implements RecyclerView.OnItemTouchListener {

    //RecyclerView recyclerView;

    private onItemClickListener my_listener;

    GestureDetector gestureDetector;

    public interface onItemClickListener{
        public void onItemClick(View view, int position);
    }


    public RecyclerViewItemClickHandler(Context context, onItemClickListener my_listener) {
        this.my_listener = my_listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }



    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        View v = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (v != null && my_listener != null && gestureDetector.onTouchEvent(motionEvent)) {
            my_listener.onItemClick(v, recyclerView.getChildPosition(v));
        }


        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
