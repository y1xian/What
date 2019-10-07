package com.yyxnb.arch.base.nav;

import android.content.Context;

public class Resource {

	public static int getId(Context paramContext, String paramString) {
		return paramContext.getResources().getIdentifier(paramString, "id",
				paramContext.getPackageName());
	}

}
