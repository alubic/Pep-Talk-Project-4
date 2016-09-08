package owlslubic.peptalkapp.views;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import owlslubic.peptalkapp.R;
import owlslubic.peptalkapp.models.PepTalkObject;
import owlslubic.peptalkapp.presenters.OnStartDragListener;
import owlslubic.peptalkapp.presenters.PepTalkFirebaseAdapter;
import owlslubic.peptalkapp.presenters.PepTalkViewHolder;
import owlslubic.peptalkapp.presenters.SimpleItemTouchHelperCallback;

public class PepTalkListActivity extends AppCompatActivity {// implements OnStartDragListener {

    private static final String TAG = "PepTalkListActivity";
    private static final String USERS = "users";
    private static final String PEPTALKS = "peptalks";
    private DatabaseReference mDbRef;
    private PepTalkFirebaseAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private DatabaseReference mPeptalkRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pep_talk_list);

//        insertContentOnNewAccountCreated();


//        getSupportActionBar().setTitle("Your Pep Talks");
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //fab launches dialog
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_peptalk_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog.launchNewPeptalkDialog(PepTalkListActivity.this);
            }
        });


        //recyclerview

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            mPeptalkRef = FirebaseDatabase.getInstance().getReference().child(USERS)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(PEPTALKS);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_peptalk_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        mFirebaseAdapter = new PepTalkFirebaseAdapter(PepTalkObject.class,
                R.layout.card_peptalk, PepTalkViewHolder.class, mPeptalkRef,
                this);//, this); took out the onstartdrag listener
        recyclerView.setAdapter(mFirebaseAdapter);



        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);//, this, mFirebaseAdapter.get);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);


    }

/*    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);

    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

/*
    //FOR SWIPE TO DISMISS
    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                xMark = ContextCompat.getDrawable(PepTalkListActivity.this, R.drawable.ic_menu_camera);//to be replaced with the trashcan icon
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) PepTalkListActivity.this.getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            //not important ebcause we're not doing drag and drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
    }
*/


    public void launchFragment(PepTalkObject model){
        MyFragment frag = new MyFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.framelayout_peptalk_frag_container, new MyFragment());

        frag.mTextViewTitle.setText(model.getTitle());
        frag.mTextViewBody.setText(model.getBody());

        ft.commit();
    }


    private void insertContentOnNewAccountCreated() {
        //TODO NEED TO FIND A WAY TO ONLY RUN THIS ONE TIME WHEN ACCOUNT IS CREATED
        //there might be some sort of auth method for like "on authenticated", idk look into it

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            Log.d(TAG, "insertContentOnNewAccountCreated: this is in the beginning of the method, so this just means the currentuser is not null");

            //TODO the below does not work so uh yeah
            if (userRef.child("peptalks") == null) {//meaning that the user and/or its children have not been added to the db bc nothing has been added yet

                Log.d(TAG, "insertContentOnNewAccountCreated: this log is from within the second if, thus userRef IS null");

                CustomDialog.writeNewChecklist("Drink a glass of water", "Hydration will help you feel better, I promise");
                CustomDialog.writeNewChecklist("Eat something", "Has it been a few hours since you last ate? Grab something with protein (nuts? hummus?) to get your body back on track");
                CustomDialog.writeNewChecklist("Move your body", "Dance around for the length of a song, go for a walk around the block, something to get your blood flowing. It'll make you feel good.");
                CustomDialog.writeNewChecklist("Step back, take a minute", "If you're feeling overwhelmed, take a moment to ground yourself. Feel your feet on the ground, you can get through this.");


                CustomDialog.writeNewPeptalk("Prepopulated peptalk 1", "Body");
                CustomDialog.writeNewPeptalk("Prepopulated peptalk 2", "Body");
                CustomDialog.writeNewPeptalk("Prepopulated peptalk 3", "Body");
                CustomDialog.writeNewPeptalk("Prepopulated peptalk 4", "Body");
            }
        }
    }
}
