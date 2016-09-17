package fr.intech.nfccommander.handlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handler for tag preferences load and save
 */
public class TagPreferencesHandler {

    private static final String PREFERENCES_KEY_TAGS = "tags_ids";

    /**
     * Save tag ID in the preferences
     * @param context       the application context
     * @param tag           the tag
     * @return              true if saved, false if not
     */
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

    /**
     * Find if tag ID in the given tags IDs list
     * @param tagID     the tag ID to find
     * @param tagsIDs   the list of tag IDs to check
     * @return          true if found, false if not
     */
    private static boolean findTagID(String tagID, List<String> tagsIDs) {
        for (String id : tagsIDs) {
            if (id.equalsIgnoreCase(tagID)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Load saved tags IDs from the preferences
     * @param context   the application context
     * @return          the list of saved tags IDs
     */
    public static List<String> loadSavedTagsIDs(Context context) {
        List<String> tagsIDs = new ArrayList<>();

        Set<String> savedTags = PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREFERENCES_KEY_TAGS, null);
        if (savedTags != null) {
            tagsIDs.addAll(savedTags);
        }

        return tagsIDs;
    }
}
