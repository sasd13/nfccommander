package fr.intech.nfccommander.activities.fragments.commanders;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import fr.intech.nfccommander.EnumCommandType;
import fr.intech.nfccommander.R;
import fr.intech.nfccommander.activities.MainActivity;
import fr.intech.nfccommander.command.SMSCommand;

public class SMSCommanderFragment extends Fragment implements ICommander {

    private static class ViewHolder {
        private EditText editTextPhoneNumber, editTextSMSMessage;
        private Button buttonSubmit;
    }

    private ViewHolder form;
    private MainActivity mainActivity;

    public static SMSCommanderFragment newInstance() {
        return new SMSCommanderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        form = new ViewHolder();
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commander_sms, container, false);

        buildView(view);

        return view;
    }

    private void buildView(View view) {
        form.editTextPhoneNumber = (EditText) view.findViewById(R.id.fragment_commander_sms_edittext_phonenumber);
        form.editTextSMSMessage = (EditText) view.findViewById(R.id.fragment_commander_sms_edittext_message);
        form.buttonSubmit = (Button) view.findViewById(R.id.fragment_commander_sms_button_submit);

        form.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCommand();
            }
        });
    }

    @Override
    public void saveCommand() {
        String phoneNumber = form.editTextPhoneNumber.getText().toString();
        String smsMessage = form.editTextSMSMessage.getText().toString();

        if (phoneNumber.trim().isEmpty() || smsMessage.trim().isEmpty()) {
            Snackbar.make(getView(), R.string.error_form, Snackbar.LENGTH_SHORT).show();
        } else {
            mainActivity.writeTag(EnumCommandType.SMS, new SMSCommand(phoneNumber, smsMessage));
        }
    }
}
