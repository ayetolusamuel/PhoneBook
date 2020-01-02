package com.ayetolu.samuel.phonebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ayetolu.samuel.phonebook.userinterface.OnFilterProductListener;
import com.ayetolu.samuel.phonebook.R;
import com.ayetolu.samuel.phonebook.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {

    private List<User> mUserList;
    private final Context mContext;
    private List<User> mFilterUserList = new ArrayList<>();
    private OnUserClickListener mListener;
    private OnFilterProductListener mFilterProductListener;

    public void setFilterProductListener(OnFilterProductListener filterProductListener) {
        mFilterProductListener = filterProductListener;
    }

    public void setListener(OnUserClickListener listener) {
        mListener = listener;
    }

    public UserAdapter(Context context) {
        mContext = context;
    }

    public void setUser(List<User> users) {
        mUserList = users;
        mFilterUserList = users;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_list_items, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        if (mFilterUserList != null) {
            User user = mFilterUserList.get(position);
            holder.fullName.setText(user.getFullName());
            holder.phoneNumber.setText(user.getPhoneNumber());

        }


    }

    @Override
    public int getItemCount() {
        if (mFilterUserList == null) {
            return 0;
        }
        return mFilterUserList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mFilterUserList.clear();
                    mFilterUserList = mUserList;
                } else {
                    ArrayList<User> filteredList = new ArrayList<>();
                    for (User row : mUserList) {

                        if (row.getFullName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    mFilterProductListener.onProductFilterCount(filteredList.size());
                    mFilterUserList = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mFilterUserList = (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fullName, phoneNumber;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            phoneNumber = itemView.findViewById(R.id.phone_number);
            fullName = itemView.findViewById(R.id.full_name);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mListener == null)
                return;
            mListener.onClickDealItem(view, mFilterUserList.get(getAdapterPosition()));
        }
    }

    public interface OnUserClickListener {
        void onClickDealItem(View view, User user);
    }

}
