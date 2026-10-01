package com.example.data.remote

import com.example.data.model.NewsArticle

object SampleNewsData {
    val initialArticles: List<NewsArticle> = listOf(
        NewsArticle(
            id = "reuters-trade-2026-01",
            titleFa = "گزارش رویترز از افزایش مبادلات تجاری منطقه‌ای ایران و توسعه کریدور ترانزیتی شمال-جنوب",
            titleOriginal = "Reuters: Iran's Regional Trade Surges Amid Expansion of North-South Transit Corridor",
            source = "Reuters (رویترز)",
            category = "economy",
            categoryFa = "اقتصاد و بازار",
            summaryFa = "خبرگزاری رویترز در گزارشی مستند به بررسی جهش در حجم مبادلات ترانزیتی ایران و کاهش ۴۰ درصدی زمان حمل کالا در مسیرهای بین‌المللی پرداخته است.",
            executiveSummaryFa = "توسعه زیرساخت‌های ریلی و بندری چابهار و بندرعباس منجر به رشد قابل توجه ترانزیت کالا و افزایش تسویه ارزی غیردلاری با کشورهای همسایه و اوراسیا شده است.",
            contentFa = "خبرگزاری رویترز گزارش می‌دهد که تلاش‌های دیپلماتیک و اقتصادی اخیر در منطقه خلیج فارس و آسیای مرکزی منجر به جهش در توافق‌نامه‌های حمل‌ونقل کالا و انرژی شده است. کارشناسان اقتصادی بین‌المللی بر این باورند که بهره‌برداری از خطوط ریلی جدید، زمان حمل کالا بین بنادر جنوبی ایران تا قفقاز را تا ۴۰ درصد کاهش داده و ترانزیت کالا را تسهیل نموده است. این گزارش می‌افزاید اتاق‌های بازرگانی منطقه‌ای در حال نهایی‌سازی سازوکارهای تسویه ارزی مشترک هستند.",
            originalContent = "Reuters reports that transit and trade volumes passing through regional corridors linked to Iran have seen notable expansion over the past quarter. Strategic infrastructure developments along key rail links have significantly cut freight travel times between southern ports and neighboring markets.",
            keyPoints = listOf(
                "رشد چشمگیر حجم تبادلات ترانزیتی کالا از طریق بنادر چابهار و بندرعباس",
                "کاهش ۴۰ درصدی زمان ترانزیت با بهره‌برداری از خطوط ریلی جدید",
                "همکاری با اتاق‌های بازرگانی منطقه برای تسویه ارزی دوجانبه"
            ),
            credibilityScore = 96,
            credibilityBadge = "رویترز - خبرگزاری تاییدشده بین‌المللی",
            publishedAt = "امروز - ۲ ساعت پیش",
            timestamp = System.currentTimeMillis() - 7200000L,
            isBookmarked = false,
            aiAnalysis = "این گزارش نشان‌دهنده ارتقای موقعیت ژئواکونومیک ایران به عنوان شاهراه ارتباطی آسیا و اروپا و تقویت درآمدهای ارزی پایدار است.",
            readTimeMinutes = 3
        ),
        NewsArticle(
            id = "ap-diplomacy-2026-02",
            titleFa = "آسوشیتدپرس: آغاز دور تازه گفت‌وگوهای دیپلماتیک منطقه‌ای در مسقط",
            titleOriginal = "Associated Press: Fresh Round of Regional Diplomatic Talks Convene in Muscat",
            source = "Associated Press (آسوشیتدپرس)",
            category = "politics",
            categoryFa = "سیاست و دیپلماسی",
            summaryFa = "آسوشیتدپرس از برگزاری گفت‌وگوهای سازنده دیپلماتیک در پایتخت عمان پیرامون تدابیر امنیت دریانوردی و همکاری‌های چندجانبه خبر داد.",
            executiveSummaryFa = "مذاکرات هیئت‌های ارشد در مسقط با تمرکز بر امنیت ناوبری دریایی، کاهش تنش‌ها و آزادسازی کانال‌های اقتصادی با ارزیابی مثبت طرفین همراه بوده است.",
            contentFa = "به گزارش خبرگزاری آسوشیتدپرس، مقامات ارشد دیپلماتیک در پایتخت عمان گردهم آمدند تا درباره تضمین امنیت ناوبری دریایی، محیط زیست دریایی و سازوکارهای تنش‌زدایی پایدار مذاکره کنند. منابع دیپلماتیک آگاه، فضای این گفت‌وگوها را عمل‌گرایانه و معطوف به نتایج اقتصادی و ثبات پایدار در منطقه ارزیابی کرده‌اند.",
            originalContent = "Diplomatic delegations convened in Muscat for constructive consultations focused on regional security frameworks, maritime safety, and stabilizing mutual economic cooperation, according to official statements monitored by the Associated Press.",
            keyPoints = listOf(
                "تمرکز گفت‌وگوها بر امنیت کشتیرانی و سازوکارهای تنش‌زدایی",
                "نقش میانجی‌گرانه سنتی سلطنت عمان در پیشبرد مذاکرات",
                "استقبال ناظران بین‌المللی از تداوم کانال‌های مستقیم دیپلماتیک"
            ),
            credibilityScore = 95,
            credibilityBadge = "آسوشیتدپرس - منبع رسمی",
            publishedAt = "امروز - ۴ ساعت پیش",
            timestamp = System.currentTimeMillis() - 14400000L,
            isBookmarked = true,
            aiAnalysis = "استمرار مذاکرات مسقط نشان‌دهنده اراده طرفین برای حفظ ثبات منطقه‌ای و اولویت دادن به سازوکارهای مسالمت‌آمیز است.",
            readTimeMinutes = 2
        ),
        NewsArticle(
            id = "afp-sports-2026-03",
            titleFa = "خبرگزاری فرانسه: قهرمانی تیم ملی کشتی آزاد ایران در جام جهانی با نمایشی درخشان",
            titleOriginal = "AFP: Iranian Freestyle Wrestlers Dominate World Cup with Commanding Team Title",
            source = "AFP (خبرگزاری فرانسه)",
            category = "sports",
            categoryFa = "ورزش و مسابقات",
            summaryFa = "خبرگزاری فرانسه در بازتاب قهرمانی مقتدرانه کشتی‌گیران آزادکار ایرانی، تسلط فنی و آمادگی بدنی بالای دلاورمردان ایران را ستود.",
            executiveSummaryFa = "تیم ملی کشتی آزاد ایران با کسب مدال‌های طلا در اوزان مختلف، عنوان قهرمانی جام جهانی را با برتری قاطع بر حریفان به دست آورد.",
            contentFa = "خبرگزاری فرانسه نوشت: کاروان کشتی آزاد ایران بار دیگر نشان داد که مهد بلامنازع این رشته ورزشی است. دلاورمردان ایرانی در دیدارهای نهایی با اتخاذ تاکتیک‌های هوشمندانه و اتکا به آمادگی جسمانی فوق‌العاده توانستند تمامی رقبا را از پیش رو بردارند و سرود پیروزی را در سالن مسابقات طنین‌انداز کنند.",
            originalContent = "Iran's wrestling squad put on a masterful display of technical prowess and resilience, securing top-podium finishes across weight classes at the Freestyle World Cup, as reported by Agence France-Presse.",
            keyPoints = listOf(
                "کسب مدال‌های زرین با نمایش بی‌نقص فنی در فینال",
                "تحسین اتحادیه جهانی کشتی از روحیه پهلوانی و تسلط ورزشکاران ایرانی",
                "کسب جام قهرمانی تیمی با بالاترین امتیازات ثبت‌شده"
            ),
            credibilityScore = 97,
            credibilityBadge = "خبرگزاری فرانسه (AFP) - منبع ورزشی معتبر",
            publishedAt = "دیروز",
            timestamp = System.currentTimeMillis() - 86400000L,
            isBookmarked = false,
            aiAnalysis = "کشتی به عنوان ورزش باستانی و هویت‌بخش ملی، جایگاه ویژه‌ای در ایجاد نشاط عمومی و همبستگی اجتماعی در جامعه ایران دارد.",
            readTimeMinutes = 2
        ),
        NewsArticle(
            id = "euronews-culture-2026-04",
            titleFa = "یورونیوز: درخشش هنر دستباف و صنایع‌دستی ایران در بی‌ینال بین‌المللی میلان",
            titleOriginal = "Euronews: Master Iranian Weavers and Artisans Captivate Milan Design Biennale",
            source = "Euronews (یورونیوز)",
            category = "culture",
            categoryFa = "فرهنگ و هنر",
            summaryFa = "پاویون هنر و صنایع‌دستی ایران در بی‌ینال میلان با استقبال چشمگیر طراحان دکوراسیون و گردشگران اروپایی مواجه شد.",
            executiveSummaryFa = "تلفیق طرح‌های اصیل فرش ابریشم ایرانی با سبک‌های مدرن دیزاین در میلان، نظر موزه‌ها و کلکسیونرهای جهانی را به خود معطوف ساخت.",
            contentFa = "یورونیوز فرهنگ گزارش داد که ترکیب طرح‌های اصیل ایرانی نظیر بته‌جقه و گل‌مرغ با مفاهیم دکوراسیون مینیمال معاصر، توجه شرکت‌های مطرح طراحی اروپا را به خود جلب کرده است. اساتید اصفهانی، تبریزی و شیرازی در کارگاه‌های زنده، مهارت‌های سنتی رنگ‌رزی گیاهی و گره‌زنی ابریشم را به نمایش گذاشتند که به عنوان میراث ناملموس بشری با تحسین گسترده همراه شد.",
            originalContent = "The Iranian artisanal pavilion at the Milan Design Biennale emerged as a standout showcase, merging centuries-old Persian silk craftsmanship with contemporary architectural sensibilities.",
            keyPoints = listOf(
                "استقبال گسترده کارشناسان هنری از هنر ابریشم‌بافی و میناکاری",
                "نمایش کارگاه‌های زنده رنگ‌رزی طبیعی با گیاهان بومی ایران",
                "عقد تفاهم‌نامه‌های همکاری فرهنگی و صادرات آثار فاخر هنری"
            ),
            credibilityScore = 93,
            credibilityBadge = "یورونیوز - بخش هنر و فرهنگ",
            publishedAt = "دیروز",
            timestamp = System.currentTimeMillis() - 95000000L,
            isBookmarked = true,
            aiAnalysis = "صنایع دستی فاخر ایرانی به عنوان سفیران فرهنگی، نقشی بی‌بدیل در تقویت دیپلماسی عمومی و معرفی تمدن چندهزار ساله دارند.",
            readTimeMinutes = 2
        ),
        NewsArticle(
            id = "bbc-society-2026-05",
            titleFa = "گزارش بی‌بی‌سی از طرح‌های توانمندسازی بانوان کارآفرین در استان‌های کویری ایران",
            titleOriginal = "BBC: Women-Led Solar and Agritech Cooperatives Expand in Desert Regions of Iran",
            source = "BBC World (بی‌بی‌سی)",
            category = "society",
            categoryFa = "جامعه و شهروندی",
            summaryFa = "شبکه جهانی بی‌بی‌سی در گزارشی به گسترش تعاونی‌های کارآفرینی بانوان در زمینه تولید گیاهان دارویی و بهره‌گیری از پنل‌های خورشیدی پرداخت.",
            executiveSummaryFa = "توسعه کسب‌وکارهای بومی مبتنی بر دانش اقلیمی و فناوری‌های کم‌آب‌بر توسط زنان در مناطق روستایی، الگویی موفق از اشتغال‌زایی پایدار ایجاد کرده است.",
            contentFa = "به گزارش بی‌بی‌سی، ایجاد تعاونی‌های خانوادگی در استان‌های یزد و کرمان برای فرآوری زعفران و گیاهان دارویی مقاوم به خشکی، سبب تحول در درآمد پایدار خانوارهای محلی شده است. این طرح‌ها با حمایت دانشگاه‌ها و مراکز تسهیل‌گری اجتماعی به سرعت در حال گسترش است و به کاهش مهاجرت به کلان‌شهرها کمک کرده است.",
            originalContent = "BBC World reports on community-driven entrepreneurship in central Iranian provinces, where women-led cooperatives are leveraging agritech innovations and climate-resilient farming to secure economic independence.",
            keyPoints = listOf(
                "اشتغال‌زایی مستقیم برای صدها بانوی متخصص در مناطق کویری",
                "استفاده از سیستم‌های نوین آبیاری قطره‌ای و انرژی پاک خورشیدی",
                "کاهش مهاجرت روستایی با رونق اقتصاد دانش‌بنیان محلی"
            ),
            credibilityScore = 91,
            credibilityBadge = "بی‌بی‌سی جهانی - گزارش اجتماعی",
            publishedAt = "۲ روز پیش",
            timestamp = System.currentTimeMillis() - 172800000L,
            isBookmarked = false,
            aiAnalysis = "توانمندسازی اقتصاد محلی و تقویت سرمایه اجتماعی زنان یکی از شاخص‌های اساسی در توسعه پایدار انسانی به شمار می‌آید.",
            readTimeMinutes = 3
        ),
        NewsArticle(
            id = "techcrunch-ai-iran-2026-06",
            titleFa = "تک‌کرانچ: پیشرفت پژوهشگران ایرانی در توسعه مدل‌های پردازش زبان طبیعی و سلامت هوشمند",
            titleOriginal = "TechCrunch: Iranian Researchers Make Strides in Healthcare AI and NLP Innovation",
            source = "Tech & Science Review (فناوری)",
            category = "tech",
            categoryFa = "علم و فناوری",
            summaryFa = "نشریات تخصصی فناوری از ارائه مقالات برجسته دانشمندان ایرانی در حوزه تشخیص زودهنگام بیماری‌ها با مدل‌های یادگیری عمیق خبر دادند.",
            executiveSummaryFa = "طراحی الگوریتم‌های هوش مصنوعی با دقت بالای ۹۷ درصد در تصویربرداری پزشکی توسط محققان دانشگاه‌های صنعتی ایران با تحسین مجامع بین‌المللی روبرو شد.",
            contentFa = "پژوهشگران دانشکده‌های مهندسی پزشکی و کامپیوتر دانشگاه‌های صنعتی شریف و تهران موفق به طراحی الگوریتم‌های نوآورانه‌ای شده‌اند که قادر است با تحلیل داده‌های ام‌آرآی و سی‌تی‌اسکن، علائم اولیه ضایعات عصبی را با دقت بالای ۹۷ درصد شناسایی کند. این دستاورد در معتبرترین ژورنال‌های فناوری به عنوان نمونه‌ای از نبوغ علمی محققان در مواجهه با چالش‌های فنی ستایش شده است.",
            originalContent = "A new suite of machine learning models developed by Iranian computational scientists demonstrated remarkable accuracy in non-invasive early disease diagnostics, garnering peer-reviewed recognition at prominent international medical informatics symposiums.",
            keyPoints = listOf(
                "دقت بالای ۹۷ درصدی در تشخیص عارضه‌های پزشکی با یادگیری ژرف",
                "نمایه مقالات در برترین ژورنال‌های علمی IEEE و Nature",
                "قابلیت اتصال به سامانه‌های تله‌مدیسین و بیمارستان‌های هوشمند"
            ),
            credibilityScore = 94,
            credibilityBadge = "ژورنال فناوری و سلامت - مرجع معتبر",
            publishedAt = "امروز - ۵ ساعت پیش",
            timestamp = System.currentTimeMillis() - 18000000L,
            isBookmarked = false,
            aiAnalysis = "پتانسیل بالای دانشگاه‌های ایران در حوزه هوش مصنوعی می‌تواند پیشران اقتصاد دیجیتال در غرب آسیا باشد.",
            readTimeMinutes = 3
        ),
        NewsArticle(
            id = "bloomberg-energy-2026-07",
            titleFa = "بلومبرگ: جهش سرمایه‌گذاری در مزارع خورشیدی فلات مرکزی ایران برای تامین برق پایدار صنایع",
            titleOriginal = "Bloomberg: Clean Energy Push Accelerates Across Central Plateau Solar Projects",
            source = "Bloomberg (بلومبرگ)",
            category = "energy",
            categoryFa = "انرژی و محیط‌زیست",
            summaryFa = "بلومبرگ در تحلیلی از بازار انرژی خاورمیانه، به شتاب‌گیری پروژه‌های نیروگاه‌های خورشیدی در مناطق کویری ایران اشاره کرد.",
            executiveSummaryFa = "اضافه شدن ظرفیت‌های مگاواتی انرژی پاک خورشیدی، ناترازی برق صنایع سنگین را در ایام اوج مصرف به شکل چشمگیری کاهش داده است.",
            contentFa = "بر اساس آمار آژانس‌های بین‌المللی انرژی، ظرفیت نصب‌شده نیروگاه‌های تجدیدپذیر خورشیدی در کویر لوت و مناطق یزد و اصفهان طی سال جاری با افزایش معناداری مواجه شده است. این روند به صنایع سنگین کمک می‌کند تا در فصول اوج مصرف برق، کسری شبکه را با تکیه بر انرژی پاک خورشیدی پوشش دهند.",
            originalContent = "Bloomberg's energy sector monitor highlights rapid rollout milestones in grid-scale solar installations across central Iran, providing critical industrial resilience and capitalizing on optimal desert solar irradiance levels.",
            keyPoints = listOf(
                "اتصال فازهای جدید نیروگاه‌های فتوولتائیک با راندمان بالا به شبکه سراسری",
                "کاهش انتشار آلاینده‌های کربنی و صرفه‌جویی در مصرف سوخت‌های فسیلی",
                "موقعیت ایده‌آل جغرافیایی فلات ایران به عنوان قطب انرژی خورشیدی منطقه"
            ),
            credibilityScore = 95,
            credibilityBadge = "بلومبرگ - مرجع تحلیل انرژی و اقتصاد",
            publishedAt = "دیروز",
            timestamp = System.currentTimeMillis() - 86400000L,
            isBookmarked = false,
            aiAnalysis = "گذار به انرژی‌های پاک ضرورتی اجتناب‌ناپذیر برای حفظ محیط‌زیست و تضمین جهش تولید صنعتی پایدار است.",
            readTimeMinutes = 2
        )
    )
}
