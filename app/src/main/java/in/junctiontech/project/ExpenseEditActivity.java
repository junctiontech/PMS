package in.junctiontech.project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.junctiontech.project.employeeproject.Expense;

public class ExpenseEditActivity extends AppCompatActivity {

    private CustomAdapter customAdapter;
    private ListView edit_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        referenceInitialization();



    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private void update()
    {
        PMSDatabase pmsDatabase = PMSDatabase.getInstance(this);
        List<Expense> expense_list = pmsDatabase.getExpenseList();

        if (expense_list != null) {
            expense_list.add(0, new Expense());
        }
        else {
            expense_list = new ArrayList<>(1);
            expense_list.add(0, new Expense());
        }
            if (customAdapter == null) {
                Log.d("ADAPTER", "CREATED");

                customAdapter = new CustomAdapter(this, expense_list);
                edit_listview.setAdapter(customAdapter);
            } else {
                Log.d("ADAPTER", "MODIFIED");
                customAdapter.clear();
                customAdapter.addAll(expense_list);
                customAdapter.notifyDataSetChanged();
            }
            /*edit_listview.setAdapter(new ArrayAdapter<>
                    (this, android.R.layout.simple_list_item_1, new String[]{"No Expense List In Database"}));*/



    }

    private void referenceInitialization() {
      edit_listview = (ListView) findViewById(R.id.edit_listView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense expense = customAdapter.getItem(position);
                Intent intent = new Intent(ExpenseEditActivity.this, ExpenseActivity.class);
                intent.putExtra("PROJECT_ID", expense.getProject_id());
                intent.putExtra("TASK_ID", expense.getTask_id());
                intent.putExtra("KEY", expense.getKey());
                if(expense.getProject_id()!=null)
                    intent.putExtra("DATA", "Edit");
                else
                    intent.putExtra("DATA", "Add");
                startActivity(intent);

             //   Utility.showToast(ExpenseEditActivity.this,((Expense)parent.getItemAtPosition(position)).getDate() );
            }
        });
/*
        edit_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                // Toast.makeText(ProjectActivity.this,position+"",Toast.LENGTH_LONG).show();
                task = customAdapter.getItem(position);
                return false;
            }
        });
        registerForContextMenu(lv);*/


    }

    private class CustomAdapter extends ArrayAdapter<Expense> { // TODO see <String> and think
        private final Context context;
        private List<Expense> expenseList;

        public CustomAdapter(Context context, List<Expense> expenseList) {
            super(context, R.layout.edit, expenseList);
            this.context = context;
            this.expenseList = expenseList;
        }

        @Override
        public Expense getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // super.getView(position, convertView, parent);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater myLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = myLayoutInflater.inflate(R.layout.edit, null);
                holder = new ViewHolder();
                holder.edit_projectId = (TextView) convertView.findViewById(R.id.edit_projectId);
                holder.edit_taskId = (TextView) convertView.findViewById(R.id.edit_taskId);
                holder.edit_description = (TextView) convertView.findViewById(R.id.edit_description);
                holder.edit_date = (TextView) convertView.findViewById(R.id.edit_date);
                holder.edit_ammount = (TextView) convertView.findViewById(R.id.edit_ammount);
                holder.edit_expense_textview=(TextView) convertView.findViewById(R.id.edit_expense_textview);
                holder.edit_expense_linearlayout=(LinearLayout) convertView.findViewById(R.id.edit_expense_linearlayout);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            Expense expense = expenseList.get(position);

            if(expense.getProject_id()!=null) {
                holder.edit_expense_textview.setVisibility(View.INVISIBLE);
                holder.edit_expense_linearlayout.setVisibility(View.VISIBLE);
                holder.edit_projectId.setText(expense.getProject_id());
                holder.edit_taskId.setText(expense.getTask_id());
                holder.edit_description.setText(expense.getDescription());
                holder.edit_date.setText(expense.getDate());
                holder.edit_ammount.setText(expense.getAmount());
            }
            else {
                holder.edit_expense_textview.setVisibility(View.VISIBLE);
                holder.edit_expense_linearlayout.setVisibility(View.INVISIBLE);
            }




            return convertView;
        }

        public class ViewHolder {
            private TextView edit_projectId, edit_taskId, edit_description, edit_date, edit_ammount, edit_expense_textview;
            private LinearLayout edit_expense_linearlayout;
            // private things are access easily in method of same class
        }
    }

}
