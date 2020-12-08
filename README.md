# Maybe More Data

Fork of Draylar's Maybe Data lib, with intention of adding optional loot tables and maybe more in the future. Why? Just think of something like a gravel ore mod.

## Usage

Add this to your mod using jitpack or something.

Example of conditional recipe:

`data/<modid>/conditional_recipes/<name>.json`

```json
{
  "condition": {
    "modid": "minecraft"
  },
  "recipe": {
    "type": "minecraft:crafting_shapeless",
    "ingredients": [
      {
        "item": "minecraft:redstone"
      }
    ],
    "result": {
      "item": "minecraft:diamond"
    }
  }
}
```

Conditional loot table:

`data/<modid>/conditional_loot_tables/blocks/<name>.json`

```json
{
  "condition": {
    "block": "mymod:my_optional_block"
  },
  "loot_table": {
    "type": "minecraft:block",
    "pools": [
      {
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": "mymod:my_optional_block"
          }
        ],
        "conditions": [
          {
            "condition": "minecraft:survives_explosion"
          }
        ]
      }
    ]
  }
}
```

Supported conditions:
- `modid` (string)
- `item` (identifier)
- `block` (identifier)
