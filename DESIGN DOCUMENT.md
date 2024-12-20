# Goal

The goal of this mod is to create an immersive thirst system for Minecraft that has depth and requires the player to be more mindful of their food and drink rather than just simply making the game more 'difficult' by adding another status bar to be maintained without any real thought. 

# The Thirst System

The player will have three states of thirst: quenched, netural, and dehydrated. The quenched state will provide the player with some small benefits, like faster mining speed or health regeneration (these are just examples, exact benefits TBD). The neutral state provides no effects, positive or negative. The dehydrated state will provide some negative effects like slowness or hunger (again, these are just examples, exact effects TBD).

By default, being dehydrated won't outright kill the player directly; instead death would come from secondary effects of not having water like faster hunger or heat stroke (will require Scorchful). However, a config will be available to enable damage for the player if they have been dehydrated for too long.

This mod will closely integrate with Scorchful, and will replace Scorchful's thirst system through the Scorchful thirst API. If the player is dehydrated, they will not be able to sweat, and the Rehydration enchantment will be able to work with this mod's thirst system. However, Scorchful will remain an optional dependency of this mod.

# Drink Effects

## Temperature

The cold/warm food effects currently built into Frostiful and Scorchful will be moved to this mod. Consuming cold and warm foods will be able to directly affect a player's temperature and temperature resistances through new status effects. These effects will add temperature over time, rather than providing an instant effect like they currently do.

## Alcohol

Alcoholic drinks (will require another mod like Brewin and Chewin or Vinery) will temporarily warm the player if they are cold. It will be temporary because after a set amount of time, the warmth will be removed and the player will actually be made even colder than they were before. This is a feature taken directly from Skyrim's Frostfall with no shame.

Alcohol will also dehydrate the player slightly, due to its diuretic effects. The amount of dehydration may depend on the strength of the alcohol (e.g. spirits will dehydrate more than wine, cider, and beer).

## Water Purification and Salinity

Water will need to be purified in order to be drunk. Water will have three states of cleanliness: dirty, clean, and pure. Water collected from the rain or from rivers, lakes, and aquifers will be dirty. Dirty water will always make the player sick when drunk, clean water will sometimes make them sick, and pure water will never make them sick.

There will be three ways to clean water: boiling, filtering, and distilling. Water will need to be cleaned through at least two of these methods into order to become fully clean. Boiling will be able to occur over campfires, in teapots (integrates with Farmer's Respite), and in furnaces. Filtering will require bamboo or possibly a custom cat tail plant in other biomes to make a filter. The filter will then be placed, and like a furance, will purify any water items placed within it over time. Distilling will occur when the water is brewed in a potion. I'm not sure if Create or any of its addons have a distilling method, but it would be cool to add that too.

Water collected from the ocean will be salty. Like alcohol, this water will make the player thirstier. The only way to remove salt should be through distillation.

The process of fully cleaning water should be able to be automated with Redstone.

## Food Rotting? 

This may be a more controversial feature, and not something to include in the initial release, but making food rot if not properly preserved (eg by storing in an ice box, salting, or smoking) could be a neat feature.
