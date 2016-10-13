# EmojiGUI
# ![alt text](https://github.com/PhilWicke/EmojiGUI/blob/master/cover.PNG "Emoji Keyboard")

Example output for pressing the :point_right:, :ok_hand: and :see_no_evil:
```
1F449 1F44C 1F648 
```

This GUI for an Emoji keyboard allows to select the Emoji to generate their Unicodes according to the [Unicode Standard](http://unicode.org/emoji/charts/full-emoji-list.html). A click on an Emoji will return a string on the console with the Unicode, notably missing the _U+_ prefix. The Emoji images are just a sample size from the entire consortium. To retrieve all Emoji, you need to crawl the [Unicode Standard](http://unicode.org/emoji/charts/full-emoji-list.html) webpage with respect to the provider you seek (Twitter, Browser, Apple etc). The given images are Twitter Emoji. The algorithm detects and implements new additions to the images file automatically and adjusts its size. The red button will be used to reset the string output.

In the EmojiCrawler repository, you can find algorithms used to crawl the Unicode webpage and others to retrieve translations and annotations for Emoji.

