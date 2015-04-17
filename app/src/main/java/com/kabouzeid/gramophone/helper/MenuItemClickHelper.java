package com.kabouzeid.gramophone.helper;

import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.kabouzeid.gramophone.R;
import com.kabouzeid.gramophone.dialogs.AddToPlaylistDialog;
import com.kabouzeid.gramophone.dialogs.DeletePlaylistDialog;
import com.kabouzeid.gramophone.dialogs.DeleteSongsDialog;
import com.kabouzeid.gramophone.dialogs.RenamePlaylistDialog;
import com.kabouzeid.gramophone.dialogs.SongDetailDialog;
import com.kabouzeid.gramophone.interfaces.PaletteColorHolder;
import com.kabouzeid.gramophone.loader.SongFilePathLoader;
import com.kabouzeid.gramophone.misc.AppKeys;
import com.kabouzeid.gramophone.model.Playlist;
import com.kabouzeid.gramophone.model.Song;
import com.kabouzeid.gramophone.ui.activities.base.AbsFabActivity;
import com.kabouzeid.gramophone.ui.activities.tageditor.SongTagEditorActivity;
import com.kabouzeid.gramophone.util.NavigationUtil;

import java.io.File;

/**
 * @author Karim Abou Zeid (kabouzeid), Aidan Follestad (afollestad)
 */
public class MenuItemClickHelper {

    public static boolean handleSongMenuClick(ActionBarActivity activity, Song song, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_from_disk:
                DeleteSongsDialog.create(song).show(activity.getSupportFragmentManager(), "DELETE_SONGS");
                return true;
            case R.id.action_add_to_playlist:
                AddToPlaylistDialog.create(song).show(activity.getSupportFragmentManager(), "ADD_PLAYLIST");
                return true;
            case R.id.action_play_next:
                MusicPlayerRemote.playNext(song);
                return true;
            case R.id.action_add_to_current_playing:
                MusicPlayerRemote.enqueue(song);
                return true;
            case R.id.action_tag_editor:
                Intent intent = new Intent(activity, SongTagEditorActivity.class);
                intent.putExtra(AppKeys.E_ID, song.id);
                if (activity instanceof PaletteColorHolder)
                    intent.putExtra(AppKeys.E_PALETTE, ((PaletteColorHolder) activity).getPaletteColor());
                activity.startActivity(intent);
                return true;
            case R.id.action_details:
                String songFilePath = SongFilePathLoader.getSongFilePath(activity, song.id);
                File songFile = new File(songFilePath);
                SongDetailDialog.create(songFile).show(activity.getSupportFragmentManager(), "SONG_DETAILS");
                return true;
            case R.id.action_go_to_album:
                Pair[] albumPairs = null;
                if (activity instanceof AbsFabActivity)
                    albumPairs = ((AbsFabActivity) activity).getSharedViewsWithFab(null);
                NavigationUtil.goToAlbum(activity, song.albumId, albumPairs);
                return true;
            case R.id.action_go_to_artist:
                Pair[] artistPairs = null;
                if (activity instanceof AbsFabActivity)
                    artistPairs = ((AbsFabActivity) activity).getSharedViewsWithFab(null);
                NavigationUtil.goToArtist(activity, song.artistId, artistPairs);
                return true;
        }
        return false;
    }

    public static boolean handlePlaylistMenuClick(ActionBarActivity activity, Playlist playlist, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rename_playlist:
                RenamePlaylistDialog.create(playlist.id).show(activity.getSupportFragmentManager(), "RENAME_PLAYLIST");
                return true;
            case R.id.action_delete_playlist:
                DeletePlaylistDialog.create(playlist.id).show(activity.getSupportFragmentManager(), "DELETE_PLAYLIST");
                return true;
        }
        return false;
    }
}