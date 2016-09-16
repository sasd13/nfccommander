package fr.intech.nfccommander.handlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TagPrefsHandler {

    private static final String PREFERENCES_KEY_TAGS = "tags_ids";

    public static void saveTag(Context context, Tag tag) {
        String tagId = new String(tag.getId(), StandardCharsets.UTF_8);

        if (!findTagInPrefs(tagId)) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putStringSet(PREFERENCES_KEY_TAGS, new HashSet<>(linkedTags));
            editor.apply();
        }
    }

    private static boolean findTagInPrefs(String tagId, List<String> tagsIds) {
        for (String id : tagsIds) {
            if (id.equalsIgnoreCase(tagId)) {
                return true;
            }
        }

        return false;
    }

    public static List<String> loadSavedTagsids(Context context) {
        List<String> tagsIds = new ArrayList<>();

        return tagsIds;
    }
}
