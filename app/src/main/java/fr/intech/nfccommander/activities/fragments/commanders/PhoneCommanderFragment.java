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
import fr.intech.nfccommander.command.PhoneCommand;

public class PhoneCommanderFragment extends Fragment implements ICommander {

    private static class ViewHolder {
        private EditText editTextPhoneNumber;
        private Button buttonSubmit;
    }

    private ViewHolder form;
    private MainActivity mainActivity;

    public static PhoneCommanderFragment newInstance() {
        return new PhoneCommanderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        form = new ViewHolder();
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commander_phone, container, false);

        buildView(view);

        return view;
    }

    private void buildView(View view) {
        form.editTextPhoneNumber = (EditText) view.findViewById(R.id.fragment_commander_phone_edittext_phonenumber);
        form.buttonSubmit = (Button) view.findViewById(R.id.fragment_commander_phone_button_submit);

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

        if (phoneNumber.trim().isEmpty()) {
            Snackbar.make(getView(), R.string.error_form, Snackbar.LENGTH_SHORT).show();
        } else {
            mainActivity.writeTag(EnumCommandType.PHONE, new PhoneCommand(phoneNumber));
        }
    }
}
