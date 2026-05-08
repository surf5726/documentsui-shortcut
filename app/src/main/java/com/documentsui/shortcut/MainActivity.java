package com.documentsui.shortcut;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.widget.Toast;

public final class MainActivity extends Activity {
    private static final String ACTION_BROWSE = "android.provider.action.BROWSE";
    private static final String[] DOCUMENTS_UI_PACKAGES = {
            "com.google.android.documentsui",
            "com.android.documentsui"
    };

    private static final String EXTERNAL_STORAGE_AUTHORITY =
            "com.android.externalstorage.documents";
    private static final String EXTRA_SHOW_ADVANCED =
            "android.provider.extra.SHOW_ADVANCED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!openFileManager()) {
            Toast.makeText(this, R.string.no_file_manager, Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private boolean openFileManager() {
        Uri primaryRoot = DocumentsContract.buildRootUri(EXTERNAL_STORAGE_AUTHORITY, "primary");
        Uri primaryDirectory = DocumentsContract.buildDocumentUri(
                EXTERNAL_STORAGE_AUTHORITY,
                "primary:");

        return tryStartInDocumentsUi(browseRoot(primaryRoot))
                || tryStartInDocumentsUi(viewRoot(primaryRoot))
                || tryStartInDocumentsUi(viewDirectory(primaryDirectory))
                || launchKnownDocumentsUi();
    }

    private boolean launchKnownDocumentsUi() {
        for (String packageName : DOCUMENTS_UI_PACKAGES) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null && tryStart(intent)) {
                return true;
            }
        }
        return false;
    }

    private Intent browseRoot(Uri rootUri) {
        Intent intent = new Intent(ACTION_BROWSE);
        intent.setDataAndType(rootUri, DocumentsContract.Root.MIME_TYPE_ITEM);
        intent.putExtra(EXTRA_SHOW_ADVANCED, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return intent;
    }

    private Intent viewRoot(Uri rootUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(rootUri, DocumentsContract.Root.MIME_TYPE_ITEM);
        intent.putExtra(EXTRA_SHOW_ADVANCED, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return intent;
    }

    private Intent viewDirectory(Uri directoryUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(directoryUri, DocumentsContract.Document.MIME_TYPE_DIR);
        intent.putExtra(EXTRA_SHOW_ADVANCED, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return intent;
    }

    private boolean tryStartInDocumentsUi(Intent intent) {
        for (String packageName : DOCUMENTS_UI_PACKAGES) {
            Intent packageIntent = new Intent(intent);
            packageIntent.setPackage(packageName);
            if (tryStart(packageIntent)) {
                return true;
            }
        }

        return tryStart(intent);
    }

    private boolean tryStart(Intent intent) {
        if (intent.resolveActivity(getPackageManager()) == null) {
            return false;
        }

        try {
            startActivity(intent);
            return true;
        } catch (ActivityNotFoundException | SecurityException ignored) {
            return false;
        }
    }
}
