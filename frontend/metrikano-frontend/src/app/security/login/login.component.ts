import { Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {LoginService} from './login.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  form: FormGroup;

  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router) {

    this.form = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(10)]]
    });

  }

  login() {

    const val = this.form.value;

    if (val.username && val.password) {

      this.loginService.loginUser(val.username, val.password)
                       .subscribe(resp => {

                                    const token = resp.body.token;

                                    sessionStorage.setItem('token', token);
                                    console.log('Пользователь вошёл в профиль');

                                    this.router.navigateByUrl('/home');

                       });

    }

  }

}
