package com.lucaskjaerozhang.wikitext_parser.preprocess.e2e;

import org.junit.jupiter.api.Test;

class SDUSTTest extends PreprocessorEndToEndTest {
  public SDUSTTest() {
    super("wikipedia", "zh", "山东科技大学");
  }

  @Test
  void sdustTest() {
    endToEndTest();
  }

  @Test
  void sdustParagraphInfoboxAndLead() {
    testPreprocessorWithString(
        """
        {{coord|36|0|3|N|120|7|30|E|display=title}}
        {{Infobox University
        |name = 山东科技大学
        |EnglishName = Shandong University of Science and Technology
        |image = [[File:Shandong_University_of_Science_and_Technology_logo.png|200px|山东科技大学校徽采用科大蓝为主色调，以英文校名缩写“SDUST”作为形象设计元素，通过字母笔划的借用和伸延造型，演变为飞鸟的图形，象征学校教育事业积极向上、腾飞发展；图形中心有4个圆球，组成花的形象，代表“科技”，象征科技之花盛开；图形外环上方为[[毛體字|毛体字]]校名“山东科技大学”，下方为英文标准字校名。<ref>{{Cite web|url = http://sdkdb.sdkd.net.cn/2011/766/2/1.html|title = 山东科技大学视觉形象识别系统摘要|accessdate = 2015-08-08|author = |date = |publisher = 山东科大报|archive-url = https://web.archive.org/web/20160130042049/http://sdkdb.sdkd.net.cn/2011/766/2/1.html|archive-date = 2016-01-30|dead-url = yes}}</ref>]]
        |motto = 惟真求新{{efn|惟真，意为求真知、探真理、究真谛、存真率、做真人；求新，意为想新事物、求新方法、图新变革、创新事业。}}
        |president = [[姚庆国]]
        |secretary  = [[罗公利]]
        |type = [[公立大学]]
        |established  = 1951年
        | Faculty = 2 800
        | Postgraduate = 5 900
        | Student = 40 000
        | Location = {{PRC}}[[山东省]]
        [[青岛市]][[黄岛区]]<br />[[泰安市]][[泰山区 (泰安市)|泰山区]]<br />[[济南市]][[天桥区]]
        | Colors = <span style="background-color:#0000FF;border:1px solid #CCC;color:#FFFFFF;padding:2px 25px">科大蓝</span>
        | campus= [[市区]]（总243公顷）<br>青岛<br>济南（203.81亩）<br>泰安（700余亩）
        | nickname = [[呼啸山庄]]{{efn|因1号教学楼门口处风大而得名。}}
        | website= {{url|http://www.sdust.edu.cn|Sdust.edu.cn}}
        | logo = [[Image:SDUSTLogo.png|250px]]
        }}
        '''山东科技大学'''，简称'''山科'''（{{lang-en|Shandong University of Science and Technology}}，[[缩写]]SDUST）是一所主体坐落于[[中华人民共和国]][[山东省]][[青岛市]][[黄岛区]]的[[公立大学]]。山科于1951年创校，几经合并、搬迁，1999年改现名，2004年主体搬迁至现址。目前在[[青岛市]]、[[泰安市]]、[[济南市]]三地办学，其中青岛校区为主校区。
        """
            .stripTrailing(),
        """
        <module name='Coordinates'><argument>coord</argument><argument>36</argument><argument>0</argument><argument>3</argument><argument>N</argument><argument>120</argument><argument>7</argument><argument>30</argument><argument>E</argument><argument>display=title</argument></module>
        <module name='Infobox'><argument>infobox</argument></module>
        '''山东科技大学'''，简称'''山科'''（<module name='lang'><argument>lang_xx_inherit</argument><argument>code=en</argument><argument>italic=unset</argument><argument>link=no</argument></module>，[[缩写]]SDUST）是一所主体坐落于[[中华人民共和国]][[山东省]][[青岛市]][[黄岛区]]的[[公立大学]]。山科于1951年创校，几经合并、搬迁，1999年改现名，2004年主体搬迁至现址。目前在[[青岛市]]、[[泰安市]]、[[济南市]]三地办学，其中青岛校区为主校区。
        """
            .stripTrailing());
  }

  @Test
  void sdustParagraphDeadLinkTemplate() {
    testPreprocessorWithString(
        "山东科技大学现有20所学院，以[[工科]]见长，为[[山东省]]省属重点高校，山东特色名校工程建设单位之一<ref>{{Cite web|url = http://cfl.qdu.edu.cn/html/rencaipeiyang/tesemingxiaozhongdianzhuanye/2013-04-08/129.html|title = 山东特色名校工程|accessdate = 2015-08-08|author = |date = 2013-04-08|publisher = 青岛大学外语学院}}{{dead link|date=2017年12月 |bot=InternetArchiveBot |fix-attempted=yes }}</ref>，[[中華人民共和國教育部|中华人民共和国教育部]][[卓越工程师教育培养计划]]实施高校，入选山东省高水平大学建设项目“冲一流”高校。<ref>{{Cite web |title=学校简介-山东科技大学 |url=https://www.sdust.edu.cn/xxgk/xxjj.htm |website=www.sdust.edu.cn |access-date=2022-06-24}}</ref><ref>{{Cite web |title=山东省教育厅 工作动态 我省确定高水平大学和高水平学科建设名单 |url=http://edu.shandong.gov.cn/art/2020/12/4/art_107094_10094440.html |website=edu.shandong.gov.cn |access-date=2022-06-24}}</ref>",
        """
        山东科技大学现有20所学院，以[[工科]]见长，为[[山东省]]省属重点高校，山东特色名校工程建设单位之一<ref><module name='citation/CS1'><argument>citation</argument><argument>CitationClass=web</argument></module><module name='Unsubst'><argument></argument><argument>date=__DATE__</argument><argument>$B=
        [[Category:錯誤使用替換引用的頁面]]<module name='Category handler'><argument>main</argument></module><sup class="noprint Inline-Template"><span style="white-space: nowrap;">&#91;[[Wikipedia:失效链接|<span title="自{{#time:Y年n月|{{#invoke:Dead link|date|2017年12月}}}}失效">永久-{zh:失效連結;zh-tw:失效連結;zh-cn:失效链接}-</span>]]&#93;</span></sup>[[Category:条目有永久失效的外部链接]]</argument></module></ref>，[[中華人民共和國教育部|中华人民共和国教育部]][[卓越工程师教育培养计划]]实施高校，入选山东省高水平大学建设项目“冲一流”高校。<ref><module name='citation/CS1'><argument>citation</argument><argument>CitationClass=web</argument></module></ref><ref><module name='citation/CS1'><argument>citation</argument><argument>CitationClass=web</argument></module></ref>
        """
            .stripTrailing());
  }

  @Test
  void sdustParagraphHistoryOpening() {
    testPreprocessorWithString(
        """
        == 校史 ==
        山东科技大学源起于1951年在淄博洪山设立的山东矿区第二煤矿职业学校和1956年建立的济南煤矿学校。
        """
            .stripTrailing(),
        """
        == 校史 ==
        山东科技大学源起于1951年在淄博洪山设立的山东矿区第二煤矿职业学校和1956年建立的济南煤矿学校。
        """
            .stripTrailing());
  }

  @Test
  void parserFunctionParametersCanContainChineseLanguageConversionMarkup() {
    testPreprocessorWithString(
        "{{#if:|ignored|[[Wikipedia:失效链接|永久-{zh:失效連結;zh-cn:失效链接}-]]}}",
        "[[Wikipedia:失效链接|永久-{zh:失效連結;zh-cn:失效链接}-]]");
  }

  @Test
  void externalLinksDoNotLeakPipesToContainingParserFunctions() {
    testPreprocessorWithString(
        "{{#if:|[http://web.archive.org/web/*/ <span>-{zh:清理;zh-cn:清理}-</span>]|[[Wikipedia:失效链接|dead link]]}}",
        "[[Wikipedia:失效链接|dead link]]");
  }

  @Test
  void externalLinkParsingDoesNotConsumeWikiLinks() {
    testPreprocessorWithString(
        "{{#if:1|[[Template:Short description]]}}", "[[Template:Short description]]");
  }
}
