package com.monke.monkeybook_modify.help;

import android.text.TextUtils;

import com.luhuiguo.chinese.ChineseUtils;
import com.monke.monkeybook_modify.bean.BookShelfBean;
import com.monke.monkeybook_modify.bean.ReplaceRuleBean;
import com.monke.monkeybook_modify.model.ReplaceRuleManager;

public class ChapterContentHelp {

    /**
     * 转繁体
     */
    public static String toTraditional(ReadBookControl readBookControl, String content) {
        switch (readBookControl.getTextConvert()) {
            case 0:
                break;
            case 1:
                content = ChineseUtils.toSimplified(content);
                break;
            case 2:
                content = ChineseUtils.toTraditional(content);
                break;
        }
        return content;
    }

    /**
     * 替换净化
     */
    public static String replaceContent(BookShelfBean mBook, String content) {
        String allLine[] = content.split("\n\u3000\u3000");
        //替换
        if (ReplaceRuleManager.getEnabled() != null && ReplaceRuleManager.getEnabled().size() > 0) {
            StringBuilder contentBuilder = new StringBuilder();
            for (String line : allLine) {
                for (ReplaceRuleBean replaceRule : ReplaceRuleManager.getEnabled()) {
                    if (TextUtils.isEmpty(replaceRule.getUseTo()) || isUseTo(mBook, replaceRule.getUseTo())) {
                        try {
                            line = line.replaceAll(replaceRule.getFixedRegex(), replaceRule.getReplacement());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                if (line.length() > 0) {
                    if (contentBuilder.length() == 0) {
                        contentBuilder.append(line);
                    } else {
                        contentBuilder.append("\n").append("\u3000\u3000").append(line);
                    }
                }
            }
            content = contentBuilder.toString();
            for (ReplaceRuleBean replaceRule : ReplaceRuleManager.getEnabled()) {
                if (TextUtils.isEmpty(replaceRule.getUseTo()) || isUseTo(mBook, replaceRule.getUseTo())) {
                    if (replaceRule.getIsRegex() && !TextUtils.isEmpty(replaceRule.getRegex()) && replaceRule.getRegex().contains("\\n")) {
                        try {
                            content = content.replaceAll(replaceRule.getRegex(), replaceRule.getReplacement());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
        return content;
    }

    private static boolean isUseTo(BookShelfBean mBook, String useTo) {
        return useTo.contains(mBook.getTag())
                || useTo.contains(mBook.getBookInfoBean().getName());
    }

}