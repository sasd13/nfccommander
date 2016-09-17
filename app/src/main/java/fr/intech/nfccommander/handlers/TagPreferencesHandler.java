package fr.intech.nfccommander.handlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagPreferencesHandler {

    private static final String PREFERENCES_KEY_TAGS = "tags_ids";

    public static boolean saveTagID(Context context, Tag tag) {
        String tagID = TagIDHandler.getStringID(tag.getId());
        List<String> savedTagsIDs = loadSavedTagsIDs(context);

        if (findTagID(tagID, savedTagsIDs)) {
            return false;
        }

        savedTagsIDs.add(tagID);

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putStringSet(PREFERENCES_KEY_TAGS, new HashSet<>(savedTagsIDs));
        editor.apply();

        return true;
    }

    private static boolean findTagID(String tagID, List<String> tagsIDs) {
        for (String id : tagsIDs) {
            if (id.equalsIgnoreCase(tagID)) {
                return true;
            }
        }

        return false;
    }

    public static List<String> loadSavedTagsIDs(Context context) {
        List<String> tagsIDs = new ArrayList<>();

        Set<String> savedTags = PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREFERENCES_KEY_TAGS, null);
        if (savedTags != null) {
            tagsIDs.addAll(savedTags);
        }

        return tagsIDs;
    }
}
