package org.smartregister.tbr.provider;

import android.content.Context;
import android.view.View;

import org.smartregister.repository.BaseRepository;

import java.util.Map;

/**
 * Created by ndegwamartin on 23/11/2017.
 */

public abstract class BaseRenderHelper {
    protected Context context;
    protected BaseRepository repository;

    protected BaseRenderHelper(Context context, BaseRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    public abstract void renderView(String baseEntityId, View view);


    public abstract void renderView(String baseEntityId, View view, Map<String, String> extraData);
}
