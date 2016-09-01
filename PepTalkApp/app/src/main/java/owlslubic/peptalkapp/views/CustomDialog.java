package owlslubic.peptalkapp.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import owlslubic.peptalkapp.R;
import owlslubic.peptalkapp.models.ChecklistItemObject;
import owlslubic.peptalkapp.models.PepTalkObject;

/**
 * Created by owlslubic on 8/30/16.
 */
public class CustomDialog extends AlertDialog {

    private static final String TAG = "CustomDialog";
    private static DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    //dont think i need a constructor but whatever
    public CustomDialog(Context context) {
        super(context);
    }
    //if all this goes well, I'll write some sort of switch statement for layouts so that there's only one method to do all dis launchin

    //push appends data to a list, so it generates a unique key every time a new child is added,
    //which can be called upon with getKey


    //will static launch methods cause a problem?

    public static void launchNewPeptalkDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_new_peptalk, null);
        builder.setView(layout);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView header = (TextView) dialog.findViewById(R.id.textview_new_peptalk);
        final EditText title = (EditText) dialog.findViewById(R.id.edittext_new_peptalk_title);
        final EditText body = (EditText) dialog.findViewById(R.id.edittext_new_peptalk);
        Button submit = (Button) dialog.findViewById(R.id.button_new_peptalk);


        //TODO set it so soft keyboard comes up automatically, and dialog accomodates it... coordinator maybe/
        //TODO set max char length for the title edittext and account for invalid input with error

        //this works
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleInput = title.getText().toString().trim();
                String bodyInput = body.getText().toString().trim();
                writeNewPeptalk(titleInput, bodyInput);//, false);//setting all as false to start with
                Log.i(TAG, "on submit click: title is " + titleInput);
                dialog.dismiss();
            }
        });

    }

    //works
    public static void launchEditPeptalkDialog(Context context, final PepTalkObject peptalk) {//don't think i need position with the firebaseadapter}, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_edit_peptalk, null);
        builder.setView(layout);

        final AlertDialog dialog = builder.create();
        dialog.show();

        //brings up existing text to edit
        final EditText editTitle = (EditText) dialog.findViewById(R.id.edittext_edit_peptalk_title);
        editTitle.setText(peptalk.getTitle());
        editTitle.setCursorVisible(true);
        editTitle.setFocusableInTouchMode(true);
        editTitle.requestFocus();

        final EditText editBody = (EditText) dialog.findViewById(R.id.edittext_edit_peptalk_body);
        editBody.setText(peptalk.getBody());
        editBody.setCursorVisible(true);
        editBody.setFocusableInTouchMode(true);
        editBody.requestFocus();

        Button b = (Button) dialog.findViewById(R.id.button_submit_edit_peptalk);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString().trim();
                String body = editBody.getText().toString().trim();
                updatePepTalk(peptalk,title,body);
                dialog.dismiss();
            }
        });

    }

    public static void launchNewChecklistDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_new_checklist, null);
        builder.setView(layout);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText editText = (EditText) dialog.findViewById(R.id.edittext_new_checklist);
        Button submit = (Button) dialog.findViewById(R.id.button_new_checklist);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editText.getText().toString().trim();
                writeNewChecklist(input);
                dialog.dismiss();
            }
        });


    }

    public static void launchEditChecklistDialog(Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_edit_checklist, null);
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    //this will be what is launched if all the checklist items are checked
    public void launchChecklistCompleteDialog() {
    }

    //in case i cant get the fragment display to work, or if I need to launch it from the peptalk list activity i just want a backup
    public void launchPeptalkDisplayDialog() {
    }


    //not sure where these methods should live

    public static void writeNewChecklist(String text) {

        final ChecklistItemObject item = new ChecklistItemObject(text);
        dbRef.child("Checklist").child(text).setValue(item, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.i(TAG, "writeNewChecklist onComplete: " + item.getText());
            }
        });
    }


    public static void writeNewPeptalk(String title, String body) {//, boolean isWidgetDefault) {
        //TODO current issue is that using title as key creates new object with new title upon edit, so...
        final PepTalkObject peptalk = new PepTalkObject(title, body);
        dbRef.child("PepTalks").child(title).setValue(peptalk, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.i(TAG, "writeNewPeptalk onComplete: " + peptalk.getTitle() + " has been written to firebase");
            }
        });
        //i'm hoping the above line will create a new object in peptalks with the title as its key, but anything could happen


    }

    public static void updatePepTalk(PepTalkObject peptalk, String title, String body) {
        dbRef.child("PepTalks").child(peptalk.getTitle()).child("title").setValue(title);
        dbRef.child("PepTalks").child(peptalk.getTitle()).child("body").setValue(body);

        //note, there may be an issue with passing in the whole value, on the website
        //they say to set the value as the string of what you're updating, but we'll see

        //TODO this method will be called on the onLongClick for the cardview in the recyclerview,
        // so address that once you've implemented firebase's recyclerview
    }

    public static void deletePepTalk(PepTalkObject peptalk) {
        dbRef.child("PepTalks").child(peptalk.getTitle()).setValue(null, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.i(TAG, "deletePepTalk onComplete: " + "success");
                Log.i(TAG, "deletePepTalk onComplete error: " + databaseError.toString());
            }
        });

    }


}
