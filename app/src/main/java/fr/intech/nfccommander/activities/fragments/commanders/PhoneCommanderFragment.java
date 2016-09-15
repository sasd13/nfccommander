package fr.intech.nfccommander.activities.fragments.commanders;

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

import fr.intech.nfccommander.command.EnumCommanderType;
import fr.intech.nfccommander.R;
import fr.intech.nfccommander.command.ICommander;
import fr.intech.nfccommander.handlers.TagIOHandler;

public class PhoneCommanderFragment extends Fragment implements ICommander {

    private static class ViewHolder {
        private EditText editTextNumber;
    }

    private ViewHolder formPhone;

    public static PhoneCommanderFragment newInstance() {
        return new PhoneCommanderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        formPhone = new ViewHolder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commander_phone, container, false);

        buildView(view);

        return view;
    }

    private void buildView(View view) {
        formPhone.editTextNumber = (EditText) view.findViewById(R.id.fragment_commander_phone_edittext_number);
    }

    @Override
    public void command(Tag tag) {
        try {
            TagIOHandler.write(tag, formPhone.editTextNumber.getText().toString(), EnumCommanderType.PHONE);
        } catch (FormatException e) {
            showError(R.string.error_tag_writing);
        } catch (IOException e) {
            showError(R.string.error_tag_writing);
        }
    }

    private void showError(@StringRes int message) {
        Toast.makeText(getContext(), getString(message), Toast.LENGTH_SHORT).show();
    }
}
