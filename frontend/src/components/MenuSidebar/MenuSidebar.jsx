import React, {useState} from "react";
import styles from "./MenuSidebar.module.scss";
import { Link } from 'react-router-dom';
import {List} from 'react-bootstrap-icons';
import SubMenu from "./SubMenu";
import { MenuData } from "./MenuData";
import { useRecoilState } from "recoil";
import { MenuSidebarState } from '../../recoil/recoil';

const MenuSidebar = () => {
  const [isOpen, setIsopen] = useRecoilState(MenuSidebarState);

  const ShowSidebar = () => {
      isOpen === true ? setIsopen(false) : setIsopen(true);
  }
  return (
      <>
          <div className="bar-container">
            <div className={`${styles.sidebar} ${isOpen == true ? styles.active : ''}`}>
              <div className={styles.sd_header}>
                  <h4 className="mb-0">사이드 바</h4>
                  <div className="btn" onClick={ShowSidebar}><List/></div>
              </div>
              <div className={styles.sd_body}>
                  <ul>
                  {MenuData.map((item, index) => {
                    return <SubMenu item={item} key={index} />;
                    })}
                  </ul>
              </div>
            </div>
            <div className={`sidebar-overlay ${isOpen == true ? 'active' : ''}`} onClick={ShowSidebar}></div>
         </div>
         
      </>
  )
}

export default MenuSidebar