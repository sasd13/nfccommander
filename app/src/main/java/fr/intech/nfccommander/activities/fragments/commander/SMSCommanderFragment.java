package fr.intech.nfccommander.activities.fragments.commander;

import android.nfc.FormatException;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fr.intech.nfccommander.EnumCommanderType;
import fr.intech.nfccommander.R;
import fr.intech.nfccommander.ICommander;
import fr.intech.nfccommander.handler.TagHandler;

public class SMSCommanderFragment extends Fragment implements ICommander {

    private static class ViewHolder {
        private EditText editTextMessage;
    }

    private ViewHolder formSMS;

    public static SMSCommanderFragment newInstance() {
        return new SMSCommanderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        formSMS = new ViewHolder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commander_sms, container, false);

        buildView(view);

        return view;
    }

    private void buildView(View view) {
        formSMS.editTextMessage = (EditText) view.findViewById(R.id.fragment_commander_sms_edittext_message);
    }

    @Override
    public void command(Tag tag) {
        try {
            TagHandler.write(tag, formSMS.editTextMessage.getText().toString(), EnumCommanderType.SMS);
        } catch (FormatException e) {
            showError(R.string.error_writing);
        } catch (IOException e) {
            showError(R.string.error_writing);
        }
    }

    private void showError(@StringRes int message) {
        Toast.makeText(getContext(), getString(message), Toast.LENGTH_SHORT).show();
    }
}
