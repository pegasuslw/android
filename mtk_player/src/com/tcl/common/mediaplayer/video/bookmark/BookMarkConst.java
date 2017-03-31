package com.tcl.common.mediaplayer.video.bookmark;

import android.net.Uri;

public interface BookMarkConst {

	public static int START_PLAY_FROM_BREAK =12;
	public static int START_PLAY_FROM_BEGIN =13;
	public static int MAX_BOOKMARK_COUNT =50;
	public static final String AUTHORITY="com.tcl.provider.BookMark";
	public static final String BOOKMARK_CONTENT_URI="content://"+AUTHORITY+"/bookmark";
}
