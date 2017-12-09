package com.avairebot.orion.database.transformers;

import com.avairebot.orion.contracts.database.transformers.Transformer;
import com.avairebot.orion.database.collection.DataRow;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class PlaylistTransformer extends Transformer {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
    private final List<PlaylistSong> songs = new ArrayList<>();

    private int id;
    private int size;
    private long guildId;
    private String name;

    public PlaylistTransformer(DataRow data) {
        super(data);

        if (hasData()) {
            id = data.getInt("id");
            size = data.getInt("size");
            guildId = data.getLong("guild_id");
            name = data.getString("name");

            if (data.has("songs") && data.getString("songs").length() > 0) {
                List<PlaylistSong> songs = GSON.fromJson(data.getString("songs"), (new TypeToken<List<PlaylistSong>>() {
                }.getType()));

                if (!songs.isEmpty()) {
                    this.songs.addAll(songs);
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public long getGuildId() {
        return guildId;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlaylistSong> getSongs() {
        return songs;
    }

    public void addSong(String title, String duration, String link) {
        PlaylistSong song = new PlaylistSong();

        song.title = title;
        song.duration = duration;
        song.link = link;

        songs.add(song);
    }

    public class PlaylistSong {
        private String title;
        private String duration;
        private String link;

        public String getTitle() {
            return title;
        }

        public String getDuration() {
            return duration;
        }

        public String getLink() {
            return link;
        }
    }
}
