import { Main } from './main';
import { Coordinate } from './coordinate';
import { Wind } from './wind';
import { Weather } from './weather';

export class CurrentWeather {
  name: string;
  coord: Coordinate;
  main: Main;
  wind: Wind;
  weather: Weather[];
}
