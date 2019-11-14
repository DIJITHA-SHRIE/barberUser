package com.barberapp.barberuser.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.pojos.SaloonMember;
import com.barberapp.barberuser.utils.TextDrawable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder>{
    private Context context;
    private ArrayList<SaloonMember> saloonMembers;
    private int lastCheckedItem = -1;

    public MemberAdapter(Context context, ArrayList<SaloonMember> saloonMembers) {
        this.context = context;
        this.saloonMembers = saloonMembers;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_list_item,viewGroup,false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder memberViewHolder, int position) {
        SaloonMember saloonMember = saloonMembers.get(position);
        TextDrawable textDrawable = new TextDrawable(saloonMember.getName(),50,context);
        memberViewHolder.imgMember.setImageDrawable(textDrawable);
        memberViewHolder.txtMemberName.setText(saloonMember.getName());
        memberViewHolder.txtMemberexpert.setText(saloonMember.getExpert_in());
        memberViewHolder.checkMember.setChecked(position==lastCheckedItem);
    }

    @Override
    public int getItemCount() {
        return saloonMembers.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgMember)
        ImageView imgMember;
        @BindView(R.id.txtMemberName)
        TextView txtMemberName;
        @BindView(R.id.txtMemberexpert)
        TextView txtMemberexpert;
        @BindView(R.id.checkMember)
        CheckBox checkMember;
        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            checkMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
