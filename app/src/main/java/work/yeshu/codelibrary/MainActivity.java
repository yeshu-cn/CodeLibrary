package work.yeshu.codelibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private MyAdatper mAdapter;
    private ArrayList<LibraryItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mAdapter = new MyAdatper(mItems, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recylerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initData() {
        mItems = new ArrayList<>();
        mItems.add(new LibraryItem(ObjectLiftCycleActivity.class));
        mItems.add(new LibraryItem(JavaTestActivity.class));
    }


    private class MyAdatper extends RecyclerView.Adapter<ViewHolder> {
        private ArrayList<LibraryItem> mItems   ;
        private LayoutInflater mLayoutInflater;
        private Context mContext;

        public MyAdatper(ArrayList<LibraryItem> items, Context context) {
            this.mItems = items;
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.list_item_library, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final LibraryItem item = mItems.get(position);

            holder.mTvTitle.setText(item.getTitle());
            holder.mTvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, item.getActivity());
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    private class LibraryItem {
        private Class<?> mCls;

        public LibraryItem(Class<?> cls) {
            this.mCls = cls;
        }

        public String getTitle(){
            return mCls.getSimpleName();
        }

        public Class<?> getActivity(){
            return mCls;
        }
    }
}
