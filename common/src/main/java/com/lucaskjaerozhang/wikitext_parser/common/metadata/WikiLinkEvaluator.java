package com.lucaskjaerozhang.wikitext_parser.common.metadata;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

public class WikiLinkEvaluator {
  private static final Map<String, String> wikiURLPrefixes =
      Map.<String, String>ofEntries(
          new AbstractMap.SimpleEntry<>(
              "acronym", "https://www.acronymfinder.com/~/search/af.aspx?string=exact&Acronym=%s"),
          new AbstractMap.SimpleEntry<>("advogato", "http://www.advogato.org/%s"),
          new AbstractMap.SimpleEntry<>("arxiv", "https://www.arxiv.org/abs/%s"),
          new AbstractMap.SimpleEntry<>("c2find", "http://c2.com/cgi/wiki?FindPage&value=%s"),
          new AbstractMap.SimpleEntry<>("cache", "https://www.google.com/search?q=cache:%s"),
          new AbstractMap.SimpleEntry<>("commons", "https://commons.wikimedia.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>(
              "dictionary",
              "http://www.dict.org/bin/Dict?Database=*&Form=Dict1&Strategy=*&Query=%s"),
          new AbstractMap.SimpleEntry<>("doi", "https://dx.doi.org/%s"),
          new AbstractMap.SimpleEntry<>("drumcorpswiki", "http://www.drumcorpswiki.com/%s"),
          new AbstractMap.SimpleEntry<>(
              "dwjwiki", "http://www.suberic.net/cgi-bin/dwj/wiki.cgi?%s"),
          new AbstractMap.SimpleEntry<>("elibre", "http://enciclopedia.us.es/index.php/%s"),
          new AbstractMap.SimpleEntry<>("emacswiki", "https://www.emacswiki.org/emacs/%s"),
          new AbstractMap.SimpleEntry<>("foldoc", "https://foldoc.org/?%s"),
          new AbstractMap.SimpleEntry<>("foxwiki", "https://fox.wikis.com/wc.dll?Wiki~%s"),
          new AbstractMap.SimpleEntry<>(
              "freebsdman", "https://www.FreeBSD.org/cgi/man.cgi?apropos=1&query=%s"),
          new AbstractMap.SimpleEntry<>("gentoo-wiki", "http://gentoo-wiki.com/%s"),
          new AbstractMap.SimpleEntry<>("google", "https://www.google.com/search?q=%s"),
          new AbstractMap.SimpleEntry<>("googlegroups", "https://groups.google.com/groups?q=%s"),
          new AbstractMap.SimpleEntry<>("hammondwiki", "http://www.dairiki.org/HammondWiki/%s"),
          new AbstractMap.SimpleEntry<>("hrwiki", "http://www.hrwiki.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("imdb", "http://www.imdb.com/find?q=%s"),
          new AbstractMap.SimpleEntry<>("kmwiki", "https://kmwiki.wikispaces.com/%s"),
          new AbstractMap.SimpleEntry<>("linuxwiki", "http://linuxwiki.de/%s"),
          new AbstractMap.SimpleEntry<>("lojban", "https://mw.lojban.org/papri/%s"),
          new AbstractMap.SimpleEntry<>("lqwiki", "http://wiki.linuxquestions.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("meatball", "http://meatballwiki.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("mediawikiwiki", "https://www.mediawiki.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("memoryalpha", "http://en.memory-alpha.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("metawiki", "http://sunir.org/apps/meta.pl?%s"),
          new AbstractMap.SimpleEntry<>("metawikimedia", "https://meta.wikimedia.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("mozillawiki", "https://wiki.mozilla.org/%s"),
          new AbstractMap.SimpleEntry<>("mw", "https://www.mediawiki.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("oeis", "https://oeis.org/%s"),
          new AbstractMap.SimpleEntry<>("openwiki", "http://openwiki.com/ow.asp?%s"),
          new AbstractMap.SimpleEntry<>("pmid", "https://www.ncbi.nlm.nih.gov/pubmed/%s"),
          new AbstractMap.SimpleEntry<>("pythoninfo", "https://wiki.python.org/moin/%s"),
          new AbstractMap.SimpleEntry<>("rfc", "https://tools.ietf.org/html/rfc%s"),
          new AbstractMap.SimpleEntry<>("s23wiki", "http://s23.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("seattlewireless", "http://seattlewireless.net/%s"),
          new AbstractMap.SimpleEntry<>("senseislibrary", "https://senseis.xmp.net/?%s"),
          new AbstractMap.SimpleEntry<>("shoutwiki", "http://www.shoutwiki.com/wiki/%s"),
          new AbstractMap.SimpleEntry<>("squeak", "http://wiki.squeak.org/squeak/%s"),
          new AbstractMap.SimpleEntry<>("tmbw", "http://www.tmbw.net/wiki/%s"),
          new AbstractMap.SimpleEntry<>("tmnet", "http://www.technomanifestos.net/?%s"),
          new AbstractMap.SimpleEntry<>("theopedia", "https://www.theopedia.com/%s"),
          new AbstractMap.SimpleEntry<>("twiki", "http://twiki.org/cgi-bin/view/%s"),
          new AbstractMap.SimpleEntry<>("uncyclopedia", "https://en.uncyclopedia.co/wiki/%s"),
          new AbstractMap.SimpleEntry<>("unreal", "https://wiki.beyondunreal.com/%s"),
          new AbstractMap.SimpleEntry<>("usemod", "http://www.usemod.com/cgi-bin/wiki.pl?%s"),
          new AbstractMap.SimpleEntry<>("wiki", "http://c2.com/cgi/wiki?%s"),
          new AbstractMap.SimpleEntry<>("wikia", "http://www.wikia.com/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikibooks", "https://en.wikibooks.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikidata", "https://www.wikidata.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikif1", "http://www.wikif1.org/%s"),
          new AbstractMap.SimpleEntry<>("wikihow", "https://www.wikihow.com/%s"),
          new AbstractMap.SimpleEntry<>("wikinfo", "http://wikinfo.co/English/index.php/%s"),
          new AbstractMap.SimpleEntry<>("wikimedia", "https://foundation.wikimedia.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikinews", "https://en.wikinews.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikipedia", "https://en.wikipedia.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikiquote", "https://en.wikiquote.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikisource", "https://wikisource.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikispecies", "https://species.wikimedia.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikiversity", "https://en.wikiversity.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikivoyage", "https://en.wikivoyage.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wikt", "https://en.wiktionary.org/wiki/%s"),
          new AbstractMap.SimpleEntry<>("wiktionary", "https://en.wiktionary.org/wiki/%s"));

  public static Optional<String> evaluateLink(String wiki, String article) {
    return wikiURLPrefixes.containsKey(wiki)
        ? Optional.of(String.format(wikiURLPrefixes.get(wiki), article))
        : Optional.empty();
  }
}
