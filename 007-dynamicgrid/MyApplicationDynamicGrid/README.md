

[DynamicGrid](https://github.com/askerov/DynamicGrid)

看DynamicGridView，在拖动时候，依次：

```
onTouchEvent
handleCellSwitch
reorderElements
getAdapterInterface().reorderItems
DynamicGridUtils.reorder
```