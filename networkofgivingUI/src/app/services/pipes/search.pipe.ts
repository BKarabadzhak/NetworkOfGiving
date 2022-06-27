import {Pipe, PipeTransform} from '@angular/core';
import {Charity} from '../../interfaces/interfaces';

@Pipe({
  name: 'searchCharities'
})
export class SearchPipe implements PipeTransform{
  transform(charities: Charity[], search = ''): Charity[] {
    if(!search.trim()){
      return charities;
    }
    return charities.filter(charity => {
      return charity.title.toLowerCase().includes(search.toLowerCase());
    });
  }

}
