package thinking.codebender.amawulire.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;

import java.util.List;

import thinking.codebender.amawulire.R;
import thinking.codebender.amawulire.models.Articles;


public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {

    Context context;
    List<Articles> mArticles;
    OnClickListener listener;
    AQuery aQuery;
    public RecyclerViewAdapter(Context context, List<Articles> mArticles) {
        this.context = context;
        this.mArticles = mArticles;
        aQuery = new AQuery(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        v.setOnClickListener(this);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Articles articles = mArticles.get(position);
        holder.title.setText(articles.getTitle());
        holder.excerpt.setText(articles.getExcerpt());
        holder.id = position;


        aQuery.id(holder.imageView).image(articles.getImage(), true, true);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    public interface OnClickListener {

        void onClick(View view, MyViewHolder holder);

    }

    public void setOnRecyclerItemListener(OnClickListener onRecyclerListener) {

        //this.listener = onRecyclerListener;


    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view, (MyViewHolder) view.getTag());

        }

    }
}
