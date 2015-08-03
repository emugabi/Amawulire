package thinking.codebender.amawulire.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thinking.codebender.amawulire.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView excerpt;
    TextView title;
    int id;

    public MyViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView)itemView.findViewById(R.id.featuredImage);
        excerpt = (TextView)itemView.findViewById(R.id.excerpt);
        title = (TextView)itemView.findViewById(R.id.title);



    }
}
