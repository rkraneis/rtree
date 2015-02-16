### converted all uses of streams to lists and for-each-loops
```
# Run complete. Total time: 00:22:35

Benchmark                                                                              Mode  Samples        Score       Error  Units
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren004         thrpt       10   261058,990 ±  5529,642  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren010         thrpt       10   305117,612 ±  8227,136  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren032         thrpt       10   151786,549 ±  4605,480  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren128         thrpt       10   436621,753 ± 82453,586  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren004    thrpt       10   288870,655 ±  3162,704  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren010    thrpt       10   355849,553 ±  8132,326  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren032    thrpt       10   238380,281 ±  3066,791  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren128    thrpt       10   130669,742 ±  2280,344  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren004                    thrpt       10   461640,509 ±  7903,263  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren010                    thrpt       10   297057,625 ±  4683,021  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren032                    thrpt       10   324749,412 ±  5308,134  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren128                    thrpt       10   518598,782 ± 51019,137  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren004               thrpt       10   187812,974 ±  3068,308  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren010               thrpt       10   177301,272 ±  8463,960  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren032               thrpt       10   136050,591 ±  2515,868  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren128               thrpt       10    70560,760 ±   634,979  ops/s
d.r.r.BenchmarksRTree.rStarTreeDeleteOneEveryOccurrenceFromGreekDataChildren010       thrpt       10   287649,407 ±  3959,567  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren004            thrpt       10   188748,272 ±  2520,652  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren010            thrpt       10   114697,768 ±  4706,005  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren032            thrpt       10    18603,672 ±   476,119  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren128            thrpt       10    71268,990 ±  1281,694  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren004       thrpt       10   181014,211 ±  2497,147  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren010       thrpt       10   118555,162 ±  1419,911  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren032       thrpt       10    24491,802 ±   421,275  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren128       thrpt       10     2678,721 ±    65,311  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren004                       thrpt       10   244880,138 ±  4548,040  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren010                       thrpt       10   442410,984 ± 35997,902  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren032                       thrpt       10   358161,306 ± 18509,515  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren128                       thrpt       10  1101173,796 ± 27905,412  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren004                  thrpt       10   285727,982 ±  4875,395  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren010                  thrpt       10   265516,747 ±  4747,013  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren032                  thrpt       10   361661,603 ±  6312,231  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren128                  thrpt       10   286690,480 ±  2437,948  ops/s
```

### reverted lambdas in Comparators to anonymous classes
```
# Run complete. Total time: 00:22:35

Benchmark                                                                              Mode  Samples       Score       Error  Units
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren004         thrpt       10  267667,721 ±  4852,026  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren010         thrpt       10  311214,127 ±  4350,329  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren032         thrpt       10  149595,796 ±  2903,964  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren128         thrpt       10  452369,655 ± 11158,103  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren004    thrpt       10  282007,554 ±  5123,978  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren010    thrpt       10  361684,582 ±  3942,688  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren032    thrpt       10  245455,337 ±  2444,514  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren128    thrpt       10  121639,534 ±  1419,797  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren004                    thrpt       10  254546,401 ±  5572,068  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren010                    thrpt       10  210248,919 ±  2829,088  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren032                    thrpt       10  243130,368 ± 23402,352  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren128                    thrpt       10  391729,295 ± 71159,741  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren004               thrpt       10  116796,954 ±  2284,596  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren010               thrpt       10  112097,983 ±  8782,597  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren032               thrpt       10   98260,622 ±   911,810  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren128               thrpt       10   53199,381 ±   581,005  ops/s
d.r.r.BenchmarksRTree.rStarTreeDeleteOneEveryOccurrenceFromGreekDataChildren010       thrpt       10  284089,720 ±  3740,091  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren004            thrpt       10  182803,589 ±  6111,033  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren010            thrpt       10  107907,631 ±  5431,309  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren032            thrpt       10   19292,291 ±   776,537  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren128            thrpt       10   73354,330 ±  1101,168  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren004       thrpt       10  178105,226 ±  3634,613  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren010       thrpt       10  117840,738 ±  1752,519  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren032       thrpt       10   23251,031 ±  2049,703  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren128       thrpt       10    2391,703 ±   128,743  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren004                       thrpt       10  179800,887 ±  3070,528  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren010                       thrpt       10  326204,582 ±  4573,282  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren032                       thrpt       10  234554,476 ± 14068,557  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren128                       thrpt       10  683996,958 ±  9307,591  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren004                  thrpt       10  181933,949 ±  1901,280  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren010                  thrpt       10  189304,836 ± 19394,954  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren032                  thrpt       10  286516,200 ±  5948,899  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren128                  thrpt       10  211111,466 ±  7768,326  ops/s
```

### streams

```
# Run complete. Total time: 00:23:35

Benchmark                                                                              Mode  Samples       Score       Error  Units
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren004         thrpt       10  195005,970 ±  2149,823  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren010         thrpt       10  207141,741 ±  3245,276  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren032         thrpt       10  104718,284 ± 16689,455  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren128         thrpt       10  318855,006 ±  5306,620  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren004    thrpt       10  191858,287 ±  3008,750  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren010    thrpt       10  138776,896 ±  8282,235  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren032    thrpt       10  111937,123 ±  2262,033  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren128    thrpt       10   52165,206 ±   565,084  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren004                    thrpt       10  221125,293 ± 23238,193  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren010                    thrpt       10  180539,475 ± 12533,338  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren032                    thrpt       10  226322,134 ± 26070,265  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren128                    thrpt       10  463133,884 ±  4319,213  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren004               thrpt       10  126094,929 ±  2060,370  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren010               thrpt       10  110483,241 ±  4279,601  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren032               thrpt       10  103943,778 ±   821,994  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren128               thrpt       10   53574,798 ±   543,947  ops/s
d.r.r.BenchmarksRTree.rStarTreeDeleteOneEveryOccurrenceFromGreekDataChildren010       thrpt       10  297124,351 ±  4571,563  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren004            thrpt       10  159543,913 ±  2158,737  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren010            thrpt       10  102437,083 ±  1650,742  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren032            thrpt       10   20528,836 ±   212,297  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren128            thrpt       10   57511,076 ±  2892,576  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren004       thrpt       10  141849,668 ±  1137,274  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren010       thrpt       10   82365,319 ± 11344,898  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren032       thrpt       10   17620,822 ±  2572,357  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren128       thrpt       10    2680,421 ±    24,393  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren004                       thrpt       10  180862,193 ±  1522,224  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren010                       thrpt       10  315916,576 ±  3272,920  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren032                       thrpt       10  300355,439 ±  5261,325  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren128                       thrpt       10  748304,377 ±  5097,096  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren004                  thrpt       10  188522,764 ±  3093,681  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren010                  thrpt       10  193485,767 ±  2443,816  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren032                  thrpt       10  262848,797 ± 18775,283  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren128                  thrpt       10  223576,465 ±  1633,724  ops/s
```

### original (only packages renamed)

```
# Run complete. Total time: 00:17:59

Benchmark                                                                                Mode  Samples       Score       Error  Units
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren004           thrpt       10  278280,023 ± 11090,593  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren010           thrpt       10  324927,568 ±  3635,911  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren032           thrpt       10  152696,683 ±  6777,775  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryInto1000EntriesMaxChildren128           thrpt       10  490583,544 ±  9202,628  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren004      thrpt       10  323040,623 ±  6516,780  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren010      thrpt       10  373289,518 ±  5703,708  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren032      thrpt       10  241230,492 ±  8489,458  ops/s
d.r.r.BenchmarksRTree.defaultRTreeInsertOneEntryIntoGreekDataEntriesMaxChildren128      thrpt       10  116877,024 ± 10220,364  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren004                      thrpt       10  786619,158 ± 50298,967  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren010                      thrpt       10  566078,391 ±  7697,384  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren032                      thrpt       10  504049,936 ± 56928,580  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOf1000PointsMaxChildren128                      thrpt       10  603640,433 ± 40036,238  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren004                 thrpt       10  416261,795 ±  7066,164  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren010                 thrpt       10  297927,393 ± 10118,773  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren032                 thrpt       10  163411,828 ±  4290,191  ops/s
d.r.r.BenchmarksRTree.defaultRTreeSearchOfGreekDataPointsMaxChildren128                 thrpt       10   69913,988 ±  1488,824  ops/s
d.r.r.BenchmarksRTree.rStarTreeDeleteOneEveryOccurrenceFromGreekDataChildren010         thrpt       10  298580,751 ± 10144,455  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren004              thrpt       10  257768,220 ± 12732,140  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren010              thrpt       10  156673,269 ±  8630,269  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren032              thrpt       10   41551,684 ±   790,128  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryInto1000EntriesMaxChildren128              thrpt       10  144618,840 ±  4115,128  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren004         thrpt       10  253574,739 ±  5553,207  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren010         thrpt       10  190937,862 ±  8098,609  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren032         thrpt       10   48670,101 ±  1887,982  ops/s
d.r.r.BenchmarksRTree.rStarTreeInsertOneEntryIntoGreekDataEntriesMaxChildren128         thrpt       10    4876,869 ±    72,422  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren004                         thrpt       10  504772,207 ±  5064,947  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren010                         thrpt       10  858676,390 ± 16131,931  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren032                         thrpt       10  605537,016 ±  9975,030  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOf1000PointsMaxChildren128                         thrpt       10  835604,187 ± 62617,369  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren004                    thrpt       10  665068,929 ± 11413,307  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren010                    thrpt       10  505506,347 ±  7781,864  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren010WithBackpressure    thrpt       10  136103,785 ±  2484,152  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren032                    thrpt       10  485354,112 ±  6875,623  ops/s
d.r.r.BenchmarksRTree.rStarTreeSearchOfGreekDataPointsMaxChildren128                    thrpt       10  321965,028 ±  6544,671  ops/s
```